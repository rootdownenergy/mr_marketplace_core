package com.rootdown.dev.notesapp.root.util

sealed class Screen(val route: String){
    object NotesScreen: Screen("notes_screen")
    object AddEditNotesScreen: Screen("add_edit_notes_screen")
    object LoginScreen: Screen("login_screen")
    object FireLoginScreen: Screen("fire_login_screen")
    object MaterialReqScreen: Screen("material_req_screen")
    object EditMaterialReqScreen: Screen("edit_material_req_screen")
    object SettingsScreen: Screen("settings_screen")
    object SplashScreen: Screen("splash_screen")
}
