package com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service

import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.MaterialReq
import kotlinx.coroutines.flow.Flow

class StorageServiceImpl : StorageService {
    override val materailReqs: Flow<List<MaterialReq>>
        get() = TODO("Not yet implemented")

    override suspend fun getMR(mrId: String): MaterialReq? {
        TODO("Not yet implemented")
    }

    override suspend fun save(mr: MaterialReq): String {
        TODO("Not yet implemented")
    }

    override suspend fun update(mr: MaterialReq) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(mrId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllForUser(userId: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }
}