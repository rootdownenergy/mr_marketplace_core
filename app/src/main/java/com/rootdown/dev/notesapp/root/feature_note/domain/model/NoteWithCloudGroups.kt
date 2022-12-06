package com.rootdown.dev.notesapp.root.feature_note.domain.model

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

@Keep
data class NoteWithCLoudGroups(
    @Embedded
    val note: Note,
    @Relation(
        parentColumn = "noteId",
        entity = CloudGroup::class,
        entityColumn = "cloudGroupId",
        associateBy = Junction(
            NoteCloudGroupCrossRef::class,
            parentColumn = "noteId",
            entityColumn = "cloudGroupId"
        )
    )
    val cloudGroups: List<CloudGroup>
)