package com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rootdown.dev.notesapp.root.MainActivity
import com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.di.TestAppModule
import com.rootdown.dev.notesapp.root.theme.MaterialMarketplaceAppTheme
import com.rootdown.dev.notesapp.root.util.Screen
import com.rootdown.dev.notesapp.root.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(TestAppModule::class)
class NotesScreenTest {

    // hilt rule to inject dependencies
    //call before every test case to insure di
    // make sure this rule is defined first with order
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    // simulate a screen
    @ExperimentalAnimationApi
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setup(){
        hiltRule.inject()
        //set up the screen
        composeRule.setContent {
            // still want to wrap around a nav obj
            val navControlller = rememberNavController()
            MaterialMarketplaceAppTheme {
                NavHost(
                    navController = navControlller,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navControlller)
                    }
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @Test
    fun clickToggleOrderSection_isVisible() {
        // get context in a test case
        //val context = ApplicationProvider.getApplicationContext<Context>()
        // how does a ui test work
        // first it needs to find a specific component in our ui
        // do action on or assertion on



        // find composable orderSection
        // find that not initially on screen
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        // now click on the order icon button
        composeRule.onNodeWithContentDescription("Sort").performClick()
        //assert that the order section is visible after performing the click
        //composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }

}