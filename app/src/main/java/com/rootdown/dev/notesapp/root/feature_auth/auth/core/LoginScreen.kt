package com.rootdown.dev.notesapp.root.feature_auth.auth.core

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rootdown.dev.notesapp.root.util.Screen
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val emailState = viewModel.stateEmail.value
    val passwordState = viewModel.statePassword.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = emailState.text,
                label = {
                    Text(
                        text = "Email",
                        style = TextStyle(
                            color = Color.Cyan,
                        )
                    )
                },
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnterEmail(it))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.White)
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordState.text,
                label = {
                    Text(
                        text = "Password",
                        style = TextStyle(
                            color = Color.Cyan,
                        )
                    )
                },
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnterPassword(it))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.White)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = emailState.text.isNotEmpty(),
                content = {
                    Text(text = "Login")
                },
                onClick = {
                    viewModel.onEvent(LoginEvent.LogIn)
                }
            )
        }
    }


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is LoginScreenViewModel.LoginUiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.msg
                    )
                }
                is LoginScreenViewModel.LoginUiEvent.SignedIn -> {
                    navController.navigate(Screen.MaterialReqScreen.route)
                }
            }
        }
    }
}