package com.rootdown.dev.notesapp.root.feature_material_req.domain.model.impl

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.perf.ktx.trace
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.User
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.AccountService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AccountService
{

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }


    override suspend fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun sendSignInEmailLink(email: String) {
        val url = "https://api.crestonegardens.com/"
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl(url)
            .setHandleCodeInApp(true)
            .setAndroidPackageName("com.rootdown.dev.notesapp", true, "11")
            .build()
        auth.sendSignInLinkToEmail(email, actionCodeSettings).await()
    }
}