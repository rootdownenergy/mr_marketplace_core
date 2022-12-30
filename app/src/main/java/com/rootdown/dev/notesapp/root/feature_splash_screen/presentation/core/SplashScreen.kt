package com.rootdown.dev.notesapp.root.feature_splash_screen.presentation.core

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rootdown.dev.notesapp.R
import com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note.TopAppBarDropdownMenu
import com.rootdown.dev.notesapp.root.feature_splash_screen.presentation.view_models.SplashViewModel
import com.rootdown.dev.notesapp.root.lib.compose_components.BasicButton
import com.rootdown.dev.notesapp.root.lib.ext.basicButton
import com.rootdown.dev.notesapp.root.util.Screen
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SplashViewModel.SplashUiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.msg
                    )
                }
                is SplashViewModel.SplashUiEvent.LaunchApp -> {
                    navController.navigate(Screen.MaterialReqScreen.route)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewModel.showError.value) {
                Text(text = stringResource(R.string.app_name))
                BasicButton(R.string.try_again, Modifier.basicButton()) { viewModel.onAppStart() }
            } else {
                CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
            }
        }
    }
}