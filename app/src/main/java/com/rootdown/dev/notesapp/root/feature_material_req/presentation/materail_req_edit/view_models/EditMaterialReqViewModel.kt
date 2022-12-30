package com.rootdown.dev.notesapp.root.feature_material_req.presentation.materail_req_edit.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rootdown.dev.notesapp.root.di.util.DefaultDispatcher
import com.rootdown.dev.notesapp.root.di.util.IoDispatcher
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.MaterialReq
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.LogService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.StorageMaterialReqService
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.materail_req_edit.core.AddEditMaterialReqEvent
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.materail_req_edit.core.MaterialReqFlagFieldState
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.materail_req_edit.core.MaterialReqTextFieldState
import com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class EditMaterialReqViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    logService: LogService,
    private val materialReqStorageService: StorageMaterialReqService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentMaterialReq: MaterialReq? = null

    private val _isHintState = mutableStateOf(true)
    val isHintState: State<Boolean> = _isHintState

    // material name state
    private val _reqMaterialName = mutableStateOf(MaterialReqTextFieldState(hint = "Enter Material Name: "))
    val reqMaterialName: State<MaterialReqTextFieldState> = _reqMaterialName

    // quantity state
    private val _reqQuantity = mutableStateOf(MaterialReqTextFieldState(hint = "Enter quantity requested: "))
    val reqQuantity: State<MaterialReqTextFieldState> = _reqQuantity

    // phase state
    private val _reqPhase = mutableStateOf(MaterialReqTextFieldState(hint = "Req for Phase: "))
    val reqPhase: State<MaterialReqTextFieldState> = _reqPhase

    // req description
    private val _reqDescription = mutableStateOf(MaterialReqTextFieldState(hint = "Req descriptions"))
    val reqDescription: State<MaterialReqTextFieldState> = _reqDescription


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("reqId")?.let { reqId ->
            try {
                viewModelScope.launch(ioDispatcher) {
                    _isHintState.value = false
                    Log.w("XXX", "In coroutine to get material req from saved state handle reqId")
                    if (reqId.toString().isNotBlank()){
                        Log.w("XXX", "In coroutine reqId: ${reqId.toString()}")
                        currentMaterialReq = materialReqStorageService.getMR(reqId.toString())
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(defaultDispatcher) {
                    logService.logNonFatalCrash(e)
                    _eventFlow.emit(
                        UIEvent.ShowSnackBar(
                            msg = "No Material Req Found With Id: $reqId"
                        )
                    )
                }
            }
        }
    }

    fun onEvent(event: AddEditMaterialReqEvent) {
        when(event) {
            is AddEditMaterialReqEvent.EnteredPhase -> {
                _reqPhase.value = reqPhase.value.copy(
                    text = event.value
                )
            }
            is AddEditMaterialReqEvent.ChangePhaseFocus -> {
                _reqPhase.value = _reqPhase.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            reqPhase.value.text.isBlank()
                )
            }
            is AddEditMaterialReqEvent.EnteredQuantity -> {
                _reqQuantity.value = reqQuantity.value.copy(
                    text = event.value
                )
            }
            is AddEditMaterialReqEvent.ChangeQuantityFocus -> {
                _reqQuantity.value = _reqQuantity.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            reqQuantity.value.text.isBlank()
                )
            }
            is AddEditMaterialReqEvent.EnteredReqName -> {
                _reqMaterialName.value = reqMaterialName.value.copy(
                    text = event.value
                )
            }
            is AddEditMaterialReqEvent.ChangeReqNameFocus -> {
                _reqMaterialName.value = _reqMaterialName.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            reqMaterialName.value.text.isBlank()
                )
            }
            is AddEditMaterialReqEvent.EnteredDescription -> {
                _reqDescription.value = reqMaterialName.value.copy(
                    text = event.value
                )
            }
            is AddEditMaterialReqEvent.ChangeDescriptionFocus -> {
                _reqDescription.value = _reqDescription.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            reqMaterialName.value.text.isBlank()
                )
            }
            is AddEditMaterialReqEvent.SaveMaterialReq -> {
                try {
                    viewModelScope.launch(ioDispatcher) {
                        val materialReq: MaterialReq = MaterialReq(
                            backlogMR = false,
                            bulkWeekMR = true,
                            dayMR = false,
                            description = reqDescription.value.text,
                            phase = reqPhase.value.text,
                            quantity = reqQuantity.value.text,
                            reqMaterial = reqMaterialName.value.text,
                            completed = true
                        )
                        materialReqStorageService.save(materialReq)
                        _eventFlow.emit(UIEvent.SaveMaterialReq)
                    }
                } catch (e: Exception) {
                    viewModelScope.launch(defaultDispatcher) {
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                msg = e.message ?: "Unknown Error"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val msg: String): UIEvent()
        object SaveMaterialReq: UIEvent()
    }

}