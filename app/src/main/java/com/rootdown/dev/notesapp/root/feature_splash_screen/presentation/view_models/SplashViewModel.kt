package com.rootdown.dev.notesapp.root.feature_splash_screen.presentation.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.rootdown.dev.notesapp.root.AppViewModel
import com.rootdown.dev.notesapp.root.di.util.DefaultDispatcher
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.AccountService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val accountService: AccountService,
    logService: LogService
) : AppViewModel(logService) {
    val showError = mutableStateOf(false)
    private val _eventFlow = MutableSharedFlow<SplashUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        onAppStart()
    }

    fun onAppStart() {
        showError.value = false
        if (accountService.hasUser) {
            viewModelScope.launch(defaultDispatcher) {
                _eventFlow.emit(
                    SplashUiEvent.LaunchApp
                )
            }
        }
        else createAnonymousAccount()
    }

    private fun createAnonymousAccount() {

        launchCatching(snackbar = false) {
            try {
                _eventFlow.emit(
                    SplashUiEvent.LaunchApp
                )
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                _eventFlow.emit(
                    SplashUiEvent.ShowSnackbar(
                        msg = "Error: ${ex.message}"
                    )
                )
            }
        }
    }

    sealed class SplashUiEvent {
        data class ShowSnackbar(val msg: String) : SplashUiEvent()
        object LaunchApp: SplashUiEvent()
    }
}