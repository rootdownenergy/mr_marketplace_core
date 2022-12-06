package com.rootdown.dev.notesapp.root.feature_note.domain.repository

import com.rootdown.dev.notesapp.root.feature_note.data.local.NoteWithCloudGroupDao
import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteCloudGroupCrossRef
import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteWithCLoudGroups
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesWithCloudGroupsRepoImpl @Inject constructor(
    private val dao: NoteWithCloudGroupDao
): NotesWithCloudGroupsRepo {

    override fun getNotesWithCloudGroups(query: Int): Flow<List<NoteWithCLoudGroups>> {
        return dao.getNotesWithCloudGroups(query)
    }

    override suspend fun insertCrossRef(ref: NoteCloudGroupCrossRef) {
        dao.insertCrossRef(ref)
    }

}