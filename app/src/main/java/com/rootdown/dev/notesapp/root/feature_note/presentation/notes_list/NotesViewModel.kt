package com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rootdown.dev.notesapp.root.di.util.DefaultDispatcher
import com.rootdown.dev.notesapp.root.di.util.IoDispatcher
import com.rootdown.dev.notesapp.root.feature_note.domain.model.Note
import com.rootdown.dev.notesapp.root.feature_note.domain.use_case.NoteUseCases
import com.rootdown.dev.notesapp.root.feature_note.domain.util.NoteOrder
import com.rootdown.dev.notesapp.root.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    //store most recent note
    private var recentlyDeletedNote: Note? = null

    //keep track of current flow
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    // state obj that represents the current note screen
    fun onEvent(event: NotesEvents) {
        when(event) {
            is NotesEvents.Order -> {
                // user clicks on the same ordering and same order type
                if(state.value.noteOrder::class == event.noteOrder::class &&
                        state.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvents.DeleteNote -> {
                viewModelScope.launch(ioDispatcher) {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvents.RestoreNote -> {
                viewModelScope.launch(ioDispatcher) {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvents.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}