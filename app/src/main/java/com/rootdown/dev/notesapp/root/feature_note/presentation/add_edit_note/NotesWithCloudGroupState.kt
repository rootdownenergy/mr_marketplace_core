package com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note

import com.rootdown.dev.notesapp.root.feature_note.domain.model.NoteWithCLoudGroups

data class NotesWithCloudGroupState(
    val noteWithCloudGroups: List<NoteWithCLoudGroups> = emptyList()
)