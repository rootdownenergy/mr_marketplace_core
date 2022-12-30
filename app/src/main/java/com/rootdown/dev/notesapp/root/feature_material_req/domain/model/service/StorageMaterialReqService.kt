package com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service

import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.MaterialReq
import kotlinx.coroutines.flow.Flow

interface StorageMaterialReqService {
    val materailReqs: Flow<List<MaterialReq>>

    suspend fun getMR(reqId: String): MaterialReq?
    suspend fun save(mr: MaterialReq): String
    suspend fun update(mr: MaterialReq)
    suspend fun delete(reqId: String)
}