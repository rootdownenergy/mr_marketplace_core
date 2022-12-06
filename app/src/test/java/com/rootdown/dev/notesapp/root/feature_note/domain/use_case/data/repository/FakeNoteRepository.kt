package com.rootdown.dev.notesapp.root.feature_note.domain.use_case.data.repository

import com.rootdown.dev.notesapp.root.feature_note.domain.model.Note
import com.rootdown.dev.notesapp.root.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository: NoteRepository {
    //
    val notes = mutableListOf<Note>()

    override fun getNotes(): Flow<List<Note>> {
        return flow { emit(notes) }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.noteId == id}
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    override suspend fun updateNote(note: Note) {
       notes.add(note)
    }
}