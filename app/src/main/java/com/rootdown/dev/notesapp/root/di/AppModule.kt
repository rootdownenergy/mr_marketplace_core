package com.rootdown.dev.notesapp.root.di

import com.rootdown.dev.notesapp.root.feature_note.data.local.NoteDatabase
import com.rootdown.dev.notesapp.root.feature_note.domain.repository.*
import com.rootdown.dev.notesapp.root.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteRepo(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideCloudGroupRepo(db: NoteDatabase): CloudGroupRepo {
        return CloudGroupRepoImpl(db.cloudGroupDao)
    }

    @Provides
    @Singleton
    fun provideNotesWithCloudGroupsRepo(db: NoteDatabase): NotesWithCloudGroupsRepo {
        return NotesWithCloudGroupsRepoImpl(db.noteWithCloudGroupDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository, repo: CloudGroupRepo, repoNotesWithCloudGroup: NotesWithCloudGroupsRepo): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNoteById(repository),
            editNote = EditNote(repository),
            addCloudGroup = AddCloudGroup(repo),
            addCrossRef = AddCrossRef(repoNotesWithCloudGroup),
            getNotesWithCloudGroups = GetNoteWithCloudGroup(repoNotesWithCloudGroup),
        )
    }
}