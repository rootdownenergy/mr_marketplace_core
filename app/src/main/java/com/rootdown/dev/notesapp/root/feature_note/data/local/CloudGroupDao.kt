package com.rootdown.dev.notesapp.root.feature_note.data.local

import androidx.room.*
import com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface CloudGroupDao {
    @Query("SELECT * FROM cloud_group")
    fun getCloudGroup(): Flow<List<CloudGroup>>

    @Query("SELECT * FROM cloud_group WHERE cloudGroupId = :id")
    suspend fun getCloudGroupById(id: Int): CloudGroup?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCloudGroup(group: CloudGroup)

    @Delete
    suspend fun deleteCloudGroup(group: CloudGroup)
}