package com.rootdown.dev.notesapp.root.feature_material_req.presentation.materail_req_edit.core

import androidx.compose.ui.focus.FocusState

sealed class AddEditMaterialReqEvent {
    data class EnteredPhase(val value: String): AddEditMaterialReqEvent()
    data class EnteredQuantity(val value: String): AddEditMaterialReqEvent()
    data class EnteredDescription(val value:  String): AddEditMaterialReqEvent()
    data class EnteredReqName(val value: String): AddEditMaterialReqEvent()
    data class ChangeReqNameFocus(val focusState: FocusState): AddEditMaterialReqEvent()
    data class ChangePhaseFocus(val focusState: FocusState): AddEditMaterialReqEvent()
    data class ChangeQuantityFocus(val focusState: FocusState): AddEditMaterialReqEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState): AddEditMaterialReqEvent()
    object SaveMaterialReq: AddEditMaterialReqEvent()
}
