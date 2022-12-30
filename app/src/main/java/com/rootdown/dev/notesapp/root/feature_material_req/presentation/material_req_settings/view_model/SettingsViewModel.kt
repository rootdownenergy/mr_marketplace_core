package com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_settings.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rootdown.dev.notesapp.root.LOGIN_SCREEN
import com.rootdown.dev.notesapp.root.SIGN_UP_SCREEN
import com.rootdown.dev.notesapp.root.SPLASH_SCREEN
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.AccountService
import com.rootdown.dev.notesapp.root.feature_material_req.domain.model.service.StorageMaterialReqService
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_settings.core.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageMaterialReqService
) : ViewModel()  {

    val uiState = accountService.currentUser.map {
        SettingsUiState(it.isAnonymous)
    }


    fun onSignOutClick() {
        viewModelScope.launch {
            accountService.signOut()
        }
    }

    fun onDeleteMyAccountClick() {
        viewModelScope.launch {
            accountService.deleteAccount()
        }
    }
}