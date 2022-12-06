package com.rootdown.dev.notesapp.root.di

import android.app.Application
import androidx.room.Room
import com.rootdown.dev.notesapp.root.feature_note.data.local.CloudGroupDao
import com.rootdown.dev.notesapp.root.feature_note.data.local.NoteDao
import com.rootdown.dev.notesapp.root.feature_note.data.local.NoteDatabase
import com.rootdown.dev.notesapp.root.feature_note.data.local.NoteWithCloudGroupDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideCloudGroupDao(db: NoteDatabase): CloudGroupDao {
        return db.cloudGroupDao
    }
    @Provides
    @Singleton
    fun provideNoteWithCloudGroupDao(db: NoteDatabase): NoteWithCloudGroupDao {
        return db.noteWithCloudGroupDao
    }
    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase): NoteDao {
        return db.noteDao
    }
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "app.db"
        ).build()
    }
}