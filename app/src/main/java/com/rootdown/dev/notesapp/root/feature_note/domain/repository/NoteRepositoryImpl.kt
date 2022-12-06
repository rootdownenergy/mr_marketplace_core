package com.rootdown.dev.notesapp.root.feature_note.domain.repository

import com.rootdown.dev.notesapp.root.feature_note.data.local.NoteDao
import com.rootdown.dev.notesapp.root.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
): NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insert(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.delete(note)
    }

    override suspend fun updateNote(note: Note) {
        dao.update(note.noteId,note.title,note.content,note.timestamp,note.color,note.ii)
    }
}