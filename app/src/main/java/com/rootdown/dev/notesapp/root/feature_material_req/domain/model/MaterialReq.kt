package com.rootdown.dev.notesapp.root.feature_material_req.domain.model

import com.google.firebase.firestore.DocumentId

data class MaterialReq(
    @DocumentId val id: String = "",
)
