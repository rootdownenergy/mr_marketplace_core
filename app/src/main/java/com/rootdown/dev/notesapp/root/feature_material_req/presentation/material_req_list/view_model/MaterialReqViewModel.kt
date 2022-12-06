package com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core.MaterialReqState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MaterialReqViewModel @Inject constructor(

): ViewModel() {

    private val _state = mutableStateOf(MaterialReqState())
    val state: State<MaterialReqState> = _state


}