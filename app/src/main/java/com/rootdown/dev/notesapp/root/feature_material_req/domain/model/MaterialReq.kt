package com.rootdown.dev.notesapp.root.feature_material_req.domain.model

import com.google.firebase.firestore.DocumentId

data class MaterialReq(
    @DocumentId val reqId: String = "",
    val backlogMR: Boolean = false,
    val bulkWeekMR: Boolean = true,
    val dayMR: Boolean = false,
    val description: String = "",
    val phase: String = "",
    val quantity: String = "",
    val reqMaterial: String = "",
    val completed: Boolean = false
)
