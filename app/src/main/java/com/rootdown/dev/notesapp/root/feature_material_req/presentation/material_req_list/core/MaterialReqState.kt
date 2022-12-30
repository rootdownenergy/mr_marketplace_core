package com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core

import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.MaterialReq

data class MaterialReqState(
    val materialReqList: List<MaterialReq> = emptyList()
)