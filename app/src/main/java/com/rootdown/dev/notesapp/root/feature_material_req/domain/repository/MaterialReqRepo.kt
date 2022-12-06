package com.rootdown.dev.notesapp.root.feature_material_req.domain.repository

import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.MaterialReq
import kotlinx.coroutines.flow.Flow

interface MaterialReqRepo {
    val materailReqs: Flow<List<MaterialReq>>

    suspend fun getMR(mrId: String): MaterialReq?

    suspend fun save(mr: MaterialReq): String
    suspend fun update(mr: MaterialReq)
    suspend fun delete(mrId: String)
    suspend fun deleteAllForUser(userId: String)
}