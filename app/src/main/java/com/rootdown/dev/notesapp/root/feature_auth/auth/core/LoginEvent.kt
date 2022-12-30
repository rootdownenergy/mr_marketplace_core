package com.rootdown.dev.notesapp.root.feature_auth.auth.core

sealed class LoginEvent{
    data class EnterPassword(val value: String): LoginEvent()
    data class EnterEmail(val value: String): LoginEvent()
    object LogIn: LoginEvent()
}