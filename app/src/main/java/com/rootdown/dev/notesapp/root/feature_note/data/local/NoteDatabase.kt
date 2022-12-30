package com.rootdown.dev.notesapp.root.feature_note.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rootdown.dev.notesapp.root.feature_material_req.data.local.dao.MaterialReqDao
import com.rootdown.dev.notesapp.root.feature_material_req.data.local.entity.MaterialReqFB
import com.rootdown.dev.notesapp.root.feature_note.data.local.util.ListStringConverter
import com.rootdown.dev.notesapp.root.feature_note.domain.model.*

@Database(
    entities = [Note::class, CloudGroup::class, NoteCloudGroupCrossRef::class, MaterialReqFB::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true
)
@TypeConverters(ListStringConverter::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao
    abstract val cloudGroupDao: CloudGroupDao
    abstract val noteWithCloudGroupDao: NoteWithCloudGroupDao
    abstract val materialReqFB: MaterialReqDao
}