package com.rootdown.dev.notesapp.cleanarchitecture

import com.google.common.truth.Truth.assertThat
import com.rootdown.dev.notesapp.root.feature_note.domain.model.Note
import com.rootdown.dev.notesapp.root.feature_note.domain.use_case.AddNote
import com.rootdown.dev.notesapp.root.feature_note.domain.use_case.data.repository.FakeNoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.math.*

class AddNoteTest {

    // which dependencies do we need here?
    // what do we need to actually create a Getnotes use case?
    // and we need the use case itself

    private lateinit var addNotes: AddNote

    // we need a private val reposiitory: NoteRepository
    private lateinit var fakeRepo: FakeNoteRepository

    @Before
    fun setup(){
        fakeRepo = FakeNoteRepository()
    }

    //test that the title is not blank
    @ExperimentalCoroutinesApi
    @Test
    fun `IsNoteTitleBlank`() = runTest {
        val note = Note(title = "hello", content = "", timestamp = 1L, color = 1, ii = 2)
        assertThat(note.title).isNotEmpty()
    }
    @ExperimentalCoroutinesApi
    @Test
    fun `IsNoteContentBlank`() = runTest {
        val note = Note(title = "hello", content = "world", timestamp = 1L, color = 1, ii = 3)
        assertThat(note.content).isNotEmpty()
    }
    @ExperimentalCoroutinesApi
    @Test
    fun `IfNoteTitleAndContentNotBlankInsert`() = runTest {
        val note = Note(title = "hello", content = "world", timestamp = 1L, color = 1, ii = 4)
        if(note.content.isNotEmpty() && note.title.isNotEmpty())
        {
            fakeRepo.insertNote(note)
            assertThat(note).isIn(fakeRepo.notes)
        }
        assertThat(note).isIn(fakeRepo.notes)
    }
}


