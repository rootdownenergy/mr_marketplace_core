package com.rootdown.dev.notesapp.root.feature_note.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rootdown.dev.notesapp.root.feature_note.data.local.util.ListStringConverter
import com.rootdown.dev.notesapp.root.feature_note.domain.model.*

@Database(
    entities = [Note::class, CloudGroup::class, NoteCloudGroupCrossRef::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(ListStringConverter::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val cloudGroupDao: CloudGroupDao
    abstract val noteWithCloudGroupDao: NoteWithCloudGroupDao
}