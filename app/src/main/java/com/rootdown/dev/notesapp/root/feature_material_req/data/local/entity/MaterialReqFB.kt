package com.rootdown.dev.notesapp.root.feature_material_req.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mr_req")
data class MaterialReqFB(
    @PrimaryKey(autoGenerate = true)
    val mReqId: Int = 0,
)
