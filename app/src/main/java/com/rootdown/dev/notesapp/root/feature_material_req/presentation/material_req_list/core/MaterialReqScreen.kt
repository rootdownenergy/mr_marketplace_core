package com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.core

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.view_model.MaterialReqViewModel
import androidx.navigation.NavController
import com.rootdown.dev.notesapp.R
import com.rootdown.dev.notesapp.root.feature_material_req.presentation.material_req_list.components.MaterialReqItem
import com.rootdown.dev.notesapp.root.lib.ext.smallSpacer
import com.rootdown.dev.notesapp.root.util.Screen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun MaterialReqScreen(
    navController: NavController,
    viewModel: MaterialReqViewModel = hiltViewModel()
) {
    val reqs = viewModel.materialReqs.collectAsStateWithLifecycle(emptyList())
    val scaffoldState = rememberScaffoldState()
    val scope =  rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.EditMaterialReqScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add MR")
            }
        },
        scaffoldState = scaffoldState,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    backgroundColor = Color.Black,
                    elevation = 1.dp,
                    title = {
                        Text(
                            modifier = Modifier,
                            text = "Sign Out"
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(MaterialReqEvents.FirebaseLogout)
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Logged out! "
                                    )
                                }
                                navController.navigate(
                                    Screen.LoginScreen.route
                                )
                            }
                        ){
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
                horizontalAlignment =
                Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = Color.Blue,
                        text = "MR Marketplace - Leader",
                        style = MaterialTheme.typography.h5
                    )
                }
                Spacer(modifier = Modifier.smallSpacer())
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(reqs.value){ req ->
                        MaterialReqItem(
                            mr = req,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Edit: ${req.reqId}"
                                        )
                                    }
                                    navController.navigate(
                                        Screen.EditMaterialReqScreen.route + "?${req.reqId}"
                                    )
                                },
                            onDeleteClick = {
                                viewModel.onEvent(MaterialReqEvents.DeleteMaterialReq(req))
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Deleted material req: $req"
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}