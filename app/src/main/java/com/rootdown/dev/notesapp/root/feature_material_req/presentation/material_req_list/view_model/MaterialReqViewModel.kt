package com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rootdown.dev.notesapp.root.*
import com.rootdown.dev.notesapp.root.di.util.DefaultDispatcher
import com.rootdown.dev.notesapp.root.di.util.IoDispatcher
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.MaterialReq
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.AccountService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.ConfigService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.StorageMaterialReqService
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core.MaterialReqActionOption
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core.MaterialReqEvents
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core.MaterialReqState
import com.rootdown.dev.notesapp.root.feature_note.domain.util.NoteOrder
import com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.NotesEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaterialReqViewModel @Inject constructor(
    private val storageService: StorageMaterialReqService,
    private val auth: AccountService,
    private val configurationService: ConfigService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {

    val materialReqs = storageService.materailReqs

    val options = mutableStateOf<List<String>>(listOf())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun loadTaskOptions() {
        val hasEditOption = configurationService.isShowReqEditBtnConfig
        options.value = MaterialReqActionOption.getOptions(hasEditOption)
    }

    fun onEvent(event: MaterialReqEvents) {
        when(event) {
            is MaterialReqEvents.FirebaseLogout -> {
                viewModelScope.launch {
                    auth.signOut()
                }
            }
            is MaterialReqEvents.DeleteMaterialReq -> {
                viewModelScope.launch {
                    storageService.delete(event.req.reqId)
                }
            }
        }
    }

    fun onReqCheckChange(mr: MaterialReq) {
        viewModelScope.launch(ioDispatcher) {
            storageService.update(mr.copy())
            _eventFlow.emit(UiEvent.EditMaterialReq)
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object EditMaterialReq: UiEvent()
    }

}