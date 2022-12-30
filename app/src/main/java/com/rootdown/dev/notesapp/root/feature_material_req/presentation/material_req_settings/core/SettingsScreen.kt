package com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_settings.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rootdown.dev.notesapp.root.feature_auth.auth.core.LoginScreen
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_settings.view_model.SettingsViewModel
import com.rootdown.dev.notesapp.root.util.Screen
import com.rootdown.dev.notesapp.root.lib.compose_components.BasicToolbar
import com.rootdown.dev.notesapp.root.lib.compose_components.DangerousCardEditor
import com.rootdown.dev.notesapp.root.lib.compose_components.RegularCardEditor
import com.rootdown.dev.notesapp.root.lib.ext.card
import com.rootdown.dev.notesapp.root.lib.ext.spacer
import com.rootdown.dev.notesapp.R.drawable as AppIcon
import com.rootdown.dev.notesapp.R.string as AppText

@ExperimentalMaterialApi
@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState(
        initial = SettingsUiState(false)
    )
    //val uiState by viewModel.uiState.collectAsState(initial = SettingsUiState(false))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolbar(AppText.settings)

        Spacer(modifier = Modifier.spacer())

        if (uiState.isAnonymousAccount) {
            RegularCardEditor(AppText.sign_in, AppIcon.ic_baseline_login_24, "", Modifier.card()) {
                navController.navigate(Screen.SettingsScreen.route)
            }

            RegularCardEditor(AppText.create_account, AppIcon.ic_baseline_person_add_24, "", Modifier.card()) {
                navController.navigate(Screen.SettingsScreen.route)
            }
        } else {
            SignOutCard {
                viewModel.onSignOutClick()
                navController.navigate(Screen.LoginScreen.route)
            }
            DeleteMyAccountCard { viewModel.onDeleteMyAccountClick() }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun SignOutCard(signOut: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    RegularCardEditor(AppText.sign_out, AppIcon.ic_baseline_exit_to_app_24, "", Modifier.card()) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    signOut()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun DeleteMyAccountCard(deleteMyAccount: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    DangerousCardEditor(
        AppText.delete_my_account,
        AppIcon.ic_baseline_delete_forever_24,
        "",
        Modifier.card()
    ) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.delete_account_title)) },
            text = { Text(stringResource(AppText.delete_account_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.delete_my_account) {
                    deleteMyAccount()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Composable
fun DialogConfirmButton(deleteMyAccount: Int, function: () -> Unit) {

}

@Composable
fun DialogCancelButton(cancel: Int, function: () -> Unit) {

}
