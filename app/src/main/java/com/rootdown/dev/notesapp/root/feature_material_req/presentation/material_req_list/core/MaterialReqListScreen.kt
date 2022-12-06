package com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.view_model.MaterialReqViewModel

@Composable
fun materialReqListScreen(
    viewModel : MaterialReqViewModel = hiltViewModel()
){

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope =  rememberCoroutineScope()

}