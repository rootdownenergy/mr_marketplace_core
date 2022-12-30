package com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rootdown.dev.notesapp.R
import com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.components.NoteItem
import com.rootdown.dev.notesapp.root.feature_note.presentation.notes_list.components.OrderSection
import com.rootdown.dev.notesapp.root.util.Screen
import com.rootdown.dev.notesapp.root.util.TestTags
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope =  rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                navController.navigate(Screen.AddEditNotesScreen.route)
            },
            backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note to MR")
            }
        },
        scaffoldState = scaffoldState,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    backgroundColor = Color.White,
                    elevation = 1.dp,
                    title = {
                        Text(text = "Sign Out")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_baseline_logout_24),
                                contentDescription = stringResource(id = R.string.sign_out)
                            )
                        }
                    }
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "MR Marketplace",
                        style = MaterialTheme.typography.h4
                    )
                    IconButton(
                        onClick = {
                            viewModel.onEvent(NotesEvents.ToggleOrderSection)
                        },
                    ){
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort"
                        )
                    }
                }
                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .testTag(TestTags.ORDER_SECTION),
                        noteOrder = state.noteOrder,
                        onOrderChange = {
                            viewModel.onEvent(NotesEvents.Order(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.notes){ note ->
                        NoteItem(
                            note = note,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        Screen.AddEditNotesScreen.route +
                                                "?noteId=${note.noteId}&noteColor=${note.color}"
                                    )
                                },
                            onDeleteClick = {
                                viewModel.onEvent(NotesEvents.DeleteNote(note))
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Note Deleted",
                                        actionLabel = "Undo"
                                    )
                                    if(result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(NotesEvents.RestoreNote)
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    )
}