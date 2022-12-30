package com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rootdown.dev.notesapp.root.di.util.DefaultDispatcher
import com.rootdown.dev.notesapp.root.di.util.IoDispatcher
import com.rootdown.dev.notesapp.root.feature_note.data.local.NoteDao
import com.rootdown.dev.notesapp.root.feature_note.domain.model.*
import com.rootdown.dev.notesapp.root.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val dao: NoteDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private lateinit var currentNote: Note

    private val _stateNWCG = mutableStateOf(NotesWithCloudGroupState())
    val state: State<NotesWithCloudGroupState> = _stateNWCG

    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter title..."))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(hint = "Enter some content..."))
    val noteContent: State<NoteTextFieldState> = _noteContent

    // must have initial value Note.noteColors.random().toArgb()
    private val _noteColor = mutableStateOf(Color.Black.toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId = -1
    private var cloudGroup = -1

    //keep track of current flow
    private var getNotesWithCloudGroupsJob: Job? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId.toString().isEmpty()) {
                viewModelScope.launch(defaultDispatcher) {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            "No Material Req Found"
                        )
                    )
                }
            } else {
                viewModelScope.launch(ioDispatcher) {
                    try {
                        noteUseCases.getNote(noteId)?.also { note ->
                            currentNote = note
                            currentNoteId = note.noteId
                            _noteTitle.value = noteTitle.value.copy(
                                text = note.title,
                                isHintVisible = false
                            )
                            _noteContent.value = noteContent.value.copy(
                                text = note.title,
                                isHintVisible = false
                            )
                            _noteColor.value = note.color
                        }
                    } catch (e: Exception){
                        viewModelScope.launch(defaultDispatcher) {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    "No Material Req Found"+e.message
                                )
                            )
                        }
                    }
                }
            }
        }
        getNoteWithCloudGroups(currentNoteId)
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                    try {
                        if (noteContent.value.text.isBlank())
                        {
                            viewModelScope.launch(defaultDispatcher) {
                                _eventFlow.emit(
                                     UiEvent.ShowSnackbar(
                                         "MR  Content Cannot Be Blank"
                                    )
                                )
                            }

                        } else if(noteTitle.value.text.isBlank()) {
                            viewModelScope.launch(defaultDispatcher) {
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        "MR Note Title Cannot Be Blank"
                                    )
                                )
                            }
                        } else if(noteTitle.value.text.isBlank() && noteContent.value.text.isBlank()){
                            viewModelScope.launch(defaultDispatcher) {
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        "MR Note Title & Content Cannot Be Blank"
                                    )
                                )
                            }
                        } else {
                            if(currentNoteId == -1) {
                                if (cloudGroup != -1)
                                {
                                    viewModelScope.launch(ioDispatcher) {
                                        noteUseCases.addNote(
                                            Note(
                                                title = noteTitle.value.text,
                                                content = noteContent.value.text,
                                                timestamp = System.currentTimeMillis(),
                                                color = noteColor.value,
                                                ii = cloudGroup
                                            )
                                        )
                                        _eventFlow.emit(UiEvent.SaveNote)

                                    }
                                }
                                else
                                {
                                    viewModelScope.launch(ioDispatcher) {
                                        noteUseCases.addNote(
                                            Note(
                                                title = noteTitle.value.text,
                                                content = noteContent.value.text,
                                                timestamp = System.currentTimeMillis(),
                                                color = noteColor.value,
                                                ii = cloudGroup ?: -1
                                            )
                                        )
                                        _eventFlow.emit(UiEvent.SaveNote)

                                    }
                                }

                            } else {
                                viewModelScope.launch(ioDispatcher) {
                                    val note = Note(
                                        noteId = currentNote.noteId,
                                        title = noteTitle.value.text,
                                        content = noteContent.value.text,
                                        timestamp = currentNote.timestamp,
                                        color = currentNote.color,
                                        ii = currentNote.ii
                                    )
                                    dao.update(note.noteId,note.title,note.content,note.timestamp,note.color, note.ii)
                                    setCrossRef()
                                    currentNoteId = currentNote.noteId
                                    _eventFlow.emit(UiEvent.SaveNote)
                                }
                            }
                        }

                    } catch(e: InvalidNoteException){
                        viewModelScope.launch(defaultDispatcher) {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = e.message ?: "Unknown Error"
                                )
                            )
                        }
                    }

            }
        }
    }
    private fun setCrossRef() {
        viewModelScope.launch(ioDispatcher) {
            //Log.w("$*$", "in setMediator")
            noteUseCases.addCrossRef(
                NoteCloudGroupCrossRef(
                    cloudGroupId = cloudGroup,
                    noteId = currentNoteId
                )
            )
        }
    }
    private fun getNoteWithCloudGroups(query: Int) {
        //val xix = CoroutineScope(SupervisorJob())
        getNotesWithCloudGroupsJob = noteUseCases.getNotesWithCloudGroups(query)
            .onEach { clouds ->
                _stateNWCG.value = state.value.copy(
                    noteWithCloudGroups = clouds
                )
            }
            .launchIn(viewModelScope)
    }
    fun setMediater(id: Int) {
        cloudGroup = id
        Log.w("$*$", "in setMediator id: ${cloudGroup.toString()}")
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}