package com.rootdown.dev.notesapp.root.feature_material_req.domain.model.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.MaterialReq
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.AccountService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.StorageMaterialReqService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.trace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageMaterialReqServiceImpl
    @Inject constructor(
        private val firestore: FirebaseFirestore,
        private val auth: AccountService
    ): StorageMaterialReqService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val materailReqs: Flow<List<MaterialReq>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                currentCollection(user.userId)
                    .snapshots()
                    .map { snapshot -> snapshot.toObjects() }
            }

    override suspend fun getMR(reqId: String): MaterialReq? =
        currentCollection(auth.currentUserId).document(reqId).get().await().toObject()

    override suspend fun save(mr: MaterialReq): String =
        currentCollection(auth.currentUserId).add(mr).await().id

    override suspend fun update(mr: MaterialReq): Unit =
        trace(UPDATE_REQ_TRACE) {
            currentCollection(auth.currentUserId).document(mr.reqId).set(mr).await()
        }

    override suspend fun delete(reqId: String) {
        currentCollection(auth.currentUserId).document(reqId).delete().await()
    }

    private fun currentCollection(uid: String): CollectionReference =
        firestore.collection(USER_COLLECTION).document(uid).collection(REQ_COLLECTION)

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
        private const val USER_COLLECTION = "user"
        private const val REQ_COLLECTION = "materialreq"
        private const val UPDATE_REQ_TRACE = "updateReq"
    }
}