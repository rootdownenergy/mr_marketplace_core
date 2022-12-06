package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteWithCLoudGroups
import com.rootdown.dev.notesapp.root.feature_note.domain.repository.NotesWithCloudGroupsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoteWithCloudGroup @Inject constructor(
    private val repository: NotesWithCloudGroupsRepo
) {
    operator fun invoke(query: Int): Flow<List<NoteWithCLoudGroups>> {
        return repository.getNotesWithCloudGroups(query)
    }
}