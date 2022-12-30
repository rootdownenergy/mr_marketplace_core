package com.rootdown.dev.notesapp.root.feature_auth.auth.core

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rootdown.dev.notesapp.R
import com.rootdown.dev.notesapp.root.AppViewModel
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.AccountService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.LogService
import com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.rootdown.dev.notesapp.root.lib.ext.isValidEmail
import com.rootdown.dev.notesapp.root.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
): AppViewModel(logService) {

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _stateEmail = mutableStateOf(LoginTextFieldState(hint = "Enter valid Email: "))
    val stateEmail: State<LoginTextFieldState> = _stateEmail

    private val _statePassword = mutableStateOf(LoginTextFieldState(hint = "Enter valid password: "))
    val statePassword: State<LoginTextFieldState> = _statePassword

    private val _stateEmailLink = mutableStateOf(LoginTextFieldState(hint = "Enter valid Email: "))
    val stateEmailLink: State<LoginTextFieldState> = _stateEmailLink


    fun onEvent(event: LoginEvent) {
        when(event){
            is LoginEvent.EnterPassword -> {
                _statePassword.value = statePassword.value.copy(
                    text = event.value
                )
            }
            is LoginEvent.EnterEmail -> {
                _stateEmail.value = stateEmail.value.copy(
                    text = event.value
                )
            }
            is LoginEvent.LogIn -> {
                try {
                    if (statePassword.value.text.isBlank()){
                        viewModelScope.launch {
                            _eventFlow.emit(
                                LoginUiEvent.ShowSnackbar(
                                    "Password cannot be blank! "
                                )
                            )
                        }
                    } else if (stateEmail.value.text.isBlank()) {
                        viewModelScope.launch {
                            _eventFlow.emit(
                                LoginUiEvent.ShowSnackbar(
                                    "Email cannot be blank! "
                                )
                            )
                        }
                    } else if (stateEmail.value.text.isNotBlank() && statePassword.value.text.isNotBlank()) {
                        onSignIn(stateEmail.value.text,statePassword.value.text)
                    } else {
                        viewModelScope.launch {
                            _eventFlow.emit(
                                LoginUiEvent.ShowSnackbar(
                                    msg = "Unknown Error"
                                )
                            )
                        }
                    }
                } catch (ex: FirebaseException) {
                    viewModelScope.launch {
                        _eventFlow.emit(
                            LoginUiEvent.ShowSnackbar(
                                msg = "Error: ${ex.message}"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun onSignIn(email: String, password: String) {
        launchCatching {
            accountService.authenticate(email, password)
            Log.w("XXX", "current user: ")
            _eventFlow.emit(
                LoginUiEvent.SignedIn
            )
        }
    }

    sealed class LoginUiEvent {
        data class ShowSnackbar(val msg: String): LoginUiEvent()
        object SignedIn: LoginUiEvent()
    }
}