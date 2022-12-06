package com.rootdown.dev.notesapp.root.feature_note.domain.model

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

@Keep
data class CLoudGroupWithNotes(
    @Embedded
    val cloudGroup: CloudGroup,
    @Relation(
        parentColumn = "cloudGroupId",
        entity = Note::class,
        entityColumn = "noteId",
        associateBy = Junction(
            NoteCloudGroupCrossRef::class,
            parentColumn = "cloudGroupId",
            entityColumn = "noteId"
        )
    )
    val notes: List<Note>
)