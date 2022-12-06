package com.rootdown.dev.notesapp.root.feature_note.domain.repository


import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteCloudGroupCrossRef
import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteWithCLoudGroups
import kotlinx.coroutines.flow.Flow

interface NotesWithCloudGroupsRepo {
    fun getNotesWithCloudGroups(query: Int): Flow<List<NoteWithCLoudGroups>>
    suspend fun insertCrossRef(ref: NoteCloudGroupCrossRef)
}