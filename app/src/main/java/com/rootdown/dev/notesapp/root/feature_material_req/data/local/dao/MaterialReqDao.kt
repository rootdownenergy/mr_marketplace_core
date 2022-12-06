package com.rootdown.dev.notesapp.root.feature_material_req.data.local.dao

import androidx.room.*
import com.rootdown.dev.notesapp.root.feature_material_req.data.local.entity.MaterialReqFB
import com.rootdown.dev.notesapp.root.feature_note.domain.model.CloudGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialReqDao {
    @Query("SELECT * FROM mr_req")
    fun getMaterialReqFB(): Flow<List<MaterialReqFB>>

    @Query("SELECT * FROM mr_req WHERE mReqId = :id")
    suspend fun getMaterialReqFBById(id: Int): CloudGroup?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterialReqFB(mr: MaterialReqFB)

    @Delete
    suspend fun deleteMaterialReqFB(mr: MaterialReqFB)
}