package com.rootdown.dev.notesapp.root

import android.content.res.Resources
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rootdown.dev.notesapp.root.feature_auth.auth.core.LoginScreen
import com.rootdown.dev.notesapp.root.feature_auth.fire_auth.core.FireLoginScreen
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core.MaterialReqScreen
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_settings.core.SettingsScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.NotesScreen
import com.rootdown.dev.notesapp.root.theme.MaterialMarketplaceAppTheme
import com.rootdown.dev.notesapp.root.util.Screen
import com.rootdown.dev.notesapp.root.util.SnackbarManager
import kotlinx.coroutines.CoroutineScope


@Composable
@ExperimentalMaterialApi
fun MaterialReqMarketplaceApp() {
    MaterialMarketplaceAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()
            val navController = rememberNavController()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.FireLoginScreen.route,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    appNavGraph(navController)
                }
            }
        }
    }
}


@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        AppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}


@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
fun NavGraphBuilder.appNavGraph(navController: NavController) {
    
    composable(route = Screen.LoginScreen.route) {
        LoginScreen(navController = navController)
    }

    composable(route = Screen.FireLoginScreen.route){
        FireLoginScreen(navController = navController)
    }
    composable(route = Screen.NotesScreen.route){
        NotesScreen(navController = navController)
    }
    composable(
        route = Screen.AddEditNotesScreen.route +
                "?noteId={noteId}&noteColor={noteColor}",
        arguments = listOf(
            navArgument(
                name = "noteId"
            ){
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument(
                name = "noteColor"
            ){
                type = NavType.IntType
                defaultValue = -1
            },
        )
    ) {
        val color: Int = it.arguments?.getInt("noteColor") ?: Color.Black.toArgb()
        AddEditNoteScreen(
            navController = navController,
            noteColor = color
        )
    }
    composable(
        route = Screen.MaterialReqScreen.route + "?reqId={reqId}",
        arguments = listOf(
            navArgument(
                name = "reqId"
            ){
                type = NavType.IntType
                defaultValue = 0
            },
        )
    ){
        MaterialReqScreen(navController = navController)
    }
    composable(
        route = Screen.SettingsScreen.route
    ) {
        SettingsScreen(navController = navController)
    }
    composable(
        route = Screen.MaterialReqScreen.route
    ) {
        MaterialReqScreen(navController = navController)
    }
}