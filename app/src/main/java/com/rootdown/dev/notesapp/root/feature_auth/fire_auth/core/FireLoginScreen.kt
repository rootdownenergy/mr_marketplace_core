package com.rootdown.dev.notesapp.root.feature_auth.fire_auth.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rootdown.dev.notesapp.R
import com.rootdown.dev.notesapp.root.lib.compose_components.*
import com.rootdown.dev.notesapp.root.lib.ext.basicButton
import com.rootdown.dev.notesapp.root.lib.ext.fieldModifier
import com.rootdown.dev.notesapp.root.lib.ext.textButton
import com.rootdown.dev.notesapp.root.util.Screen

@Composable
fun FireLoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    BasicToolbar(R.string.login_details)

    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())

        BasicButton(R.string.fui_sign_in_anonymously, Modifier.basicButton()) {
            viewModel.onSignInClick()
            navController.navigate(Screen.MaterialReqScreen.route)
        }

        BasicTextButton(R.string.forgot_password, Modifier.textButton()) {
            viewModel.onForgotPasswordClick()
        }
    }
}