package com.rootdown.dev.notesapp.root.feature_material_req.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.rootdown.dev.notesapp.root.feature_note.domain.model.Note



data class NoteAndMaterialReqFB(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "mReqId"
    )
    val materialReqFB: MaterialReqFB
)