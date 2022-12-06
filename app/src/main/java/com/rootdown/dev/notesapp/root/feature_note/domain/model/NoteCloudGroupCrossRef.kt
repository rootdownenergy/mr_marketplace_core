package com.rootdown.dev.notesapp.root.feature_note.domain.model

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Keep
@Entity(tableName = "cloud_groups",
    primaryKeys = ["cloudGroupId", "noteId"],
    indices = [Index(value = ["noteId", "cloudGroupId"])],
    inheritSuperIndices = true
)
data class NoteCloudGroupCrossRef(
    var cloudGroupId: Int = 0,
    var noteId: Int = 0,
)






