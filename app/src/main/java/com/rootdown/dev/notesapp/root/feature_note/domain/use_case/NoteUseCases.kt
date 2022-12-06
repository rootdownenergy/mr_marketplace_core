package com.rootdown.dev.notesapp.root.feature_note.domain.use_case

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNoteById,
    val editNote: EditNote,
    val addCloudGroup: AddCloudGroup,
    val addCrossRef: AddCrossRef,
    val getNotesWithCloudGroups: GetNoteWithCloudGroup,
)
