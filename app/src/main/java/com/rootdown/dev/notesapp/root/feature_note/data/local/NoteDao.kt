package com.rootdown.dev.notesapp.root.feature_note.data.local

import androidx.room.*
import com.rootdown.dev.notesapp.root.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE noteId = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("UPDATE note SET noteId = :ID, title = :title, content = :content, timestamp = :timestamp, color = :color, ii = :ii WHERE noteId = :ID")
    suspend fun update(ID: Int, title: String, content: String, timestamp: Long, color: Int, ii: Int)
    //@Update
    //suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}