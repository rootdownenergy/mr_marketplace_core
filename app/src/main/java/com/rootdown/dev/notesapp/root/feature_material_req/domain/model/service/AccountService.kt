package com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service

import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.UserFirebase
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<UserFirebase>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}