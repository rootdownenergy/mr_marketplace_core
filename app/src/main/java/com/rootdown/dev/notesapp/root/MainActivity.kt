package com.rootdown.dev.notesapp.root


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rootdown.dev.notesapp.R
import com.rootdown.dev.notesapp.root.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.rootdown.dev.notesapp.root.feature_auth.auth.core.LoginScreen
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.materail_req_edit.core.EditMaterialReqScreen
import com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.NotesScreen
import com.rootdown.dev.notesapp.root.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core.MaterialReqScreen
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_settings.core.SettingsScreen
import com.rootdown.dev.notesapp.root.feature_splash_screen.presentation.core.SplashScreen
import com.rootdown.dev.notesapp.root.theme.MaterialMarketplaceAppTheme


@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContent {
            MaterialMarketplaceAppTheme {
                Surface(
                ){
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginScreen.route
                    ){
                        composable(route = Screen.SplashScreen.route){
                            SplashScreen(navController = navController)
                        }
                        composable(route = Screen.LoginScreen.route){
                            LoginScreen(navController = navController)
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
                            route = Screen.EditMaterialReqScreen.route + "?reqId={reqId}",
                            arguments = listOf(
                                navArgument(
                                    name = "reqId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            EditMaterialReqScreen(navController = navController)
                        }
                        composable(
                            route = Screen.MaterialReqScreen.route
                        ) {
                            MaterialReqScreen(navController = navController)
                        }
                        composable(
                            route = Screen.SettingsScreen.route
                        ) {
                            SettingsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}