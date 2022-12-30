package com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core

import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.MaterialReq

sealed class MaterialReqEvents {
    data class DeleteMaterialReq(val req: MaterialReq): MaterialReqEvents()
    object FirebaseLogout: MaterialReqEvents()
}