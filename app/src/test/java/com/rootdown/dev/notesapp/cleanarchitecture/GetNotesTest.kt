package com.rootdown.dev.notesapp.cleanarchitecture

import com.google.common.truth.Truth.assertThat
import com.rootdown.dev.notesapp.root.feature_note.domain.model.Note
import com.rootdown.dev.notesapp.root.feature_note.domain.use_case.GetNotes
import com.rootdown.dev.notesapp.root.feature_note.domain.use_case.data.repository.FakeNoteRepository
import com.rootdown.dev.notesapp.root.feature_note.domain.util.NoteOrder
import com.rootdown.dev.notesapp.root.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {
    // which dependencies do we need here
    // what do we need to actually create a Getnotes use case?
    // and we need the use case itself

    // we need a private val reposiitory: NoteRepository
    private lateinit var getNotes: GetNotes

    private lateinit var fakeRepo: FakeNoteRepository

    // create a fakerepo
    // initiaize some objs
    @Before
    fun setUp(){
        fakeRepo = FakeNoteRepository()
        getNotes = GetNotes(fakeRepo)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index,
                    ii = index
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeRepo.insertNote(it) }
        }

    }

    @Test
    fun `OrderNotesByTitleAscendingCorrectOrder`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        for(i in 0..notes.size - 2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `OrderNotesByTitleDescendingCorrectOrder`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()
        for(i in 0..notes.size - 2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `OrderNotesByDateAscendingCorrectOrder`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()
        for(i in 0..notes.size - 2){
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `OrderNotesByDateDescendingCorrectOrder`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()
        for(i in 0..notes.size - 2){
            assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `OrderNotesByColorAscendingCorrectOrder`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()
        for(i in 0..notes.size - 2){
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }

    @Test
    fun `OrderNotesByColorDescendingCorrectOrder`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()
        for(i in 0..notes.size - 2){
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }

}