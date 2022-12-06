package com.rootdown.dev.notesapp.root.feature_note.data.local

import androidx.room.*
import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteCloudGroupCrossRef
import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteWithCLoudGroups
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteWithCloudGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossRef(ref: NoteCloudGroupCrossRef)
    @Transaction
    @Query("SELECT * FROM note WHERE noteId = :query")
    fun getNotesWithCloudGroups(query: Int): Flow<List<NoteWithCLoudGroups>>
}