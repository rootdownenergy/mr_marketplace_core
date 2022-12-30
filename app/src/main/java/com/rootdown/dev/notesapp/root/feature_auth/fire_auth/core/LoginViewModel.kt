package com.rootdown.dev.notesapp.root.feature_auth.fire_auth.core

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import com.rootdown.dev.notesapp.R
import com.rootdown.dev.notesapp.root.AppViewModel
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.AccountService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.LogService
import com.rootdown.dev.notesapp.root.lib.ext.isValidEmail
import com.rootdown.dev.notesapp.root.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : AppViewModel(logService) {


    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            SnackbarManager.showMessage(R.string.email)
        }
    }
}