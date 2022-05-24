package com.example.examenma.clientSectionScreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.examenma.clientSectionScreen.viewmodel.ClientSectionViewModel
import com.example.examenma.ownerSectionScreen.domain.Entity
import kotlinx.coroutines.launch

internal var shouldGetListFromDatabase = true

@Composable
fun ClientSectionScreen(
    viewModel: ClientSectionViewModel = hiltViewModel(),
    controller: NavController
) {
    val listOfEntities by viewModel.listOfEntities.observeAsState(listOf())

    val context = LocalContext.current

    val progressIndicatorVisibility = remember { mutableStateOf(false) }

    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    val snackbarCoroutineScope = rememberCoroutineScope()

    val showError: (String) -> Unit = {
        snackbarCoroutineScope.launch {
            snackbarHostState.value.showSnackbar(
                message = it,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    if (shouldGetListFromDatabase) {
        viewModel.loadEntities(context, showError, progressIndicatorVisibility)
        shouldGetListFromDatabase = false
    }

    viewModel.isRetrievedSuccessfully.observe(LocalLifecycleOwner.current) {
        if (it == false) {
            Toast.makeText(
                context,
                "There was a problem in retrieving the Products!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            viewModel.filterEntities()
        }
    }

    BackHandler {
        shouldGetListFromDatabase = true
        controller.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Client Section") },
                navigationIcon = {
                    IconButton(onClick = {
                        shouldGetListFromDatabase = true
                        controller.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (progressIndicatorVisibility.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(100.dp)
                    )
                }
                if (!progressIndicatorVisibility.value) {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(20.dp, 10.dp)
                    ) {
                        items(listOfEntities) { item ->
                            SingleTableItem(
                                entity = item
                            )
                        }
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState.value,
                snackbar = {
                    Snackbar(
                        action = {
                            if (listOfEntities.isEmpty()) {
                                Button(onClick = {
                                    snackbarHostState.value.currentSnackbarData?.dismiss()
                                    viewModel.loadEntities(
                                        context, showError, progressIndicatorVisibility
                                    )
                                }) {
                                    Text("RETRY")
                                }
                            } else {
                                Button(onClick = {
                                    snackbarHostState.value.currentSnackbarData?.dismiss()
                                }) {
                                    Text("CLOSE")
                                }
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                    ) { Text(text = it.message) }
                }
            )
        }
    )
}

@Preview
@OptIn(ExperimentalCoilApi::class)
@Composable
fun SingleTableItem(
    entity: Entity = Entity(id = 13, nume = "Test", medie1 = 9, medie2 = 8, medieAll = 8, status = true),
    onTableClick: (Entity) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .size(0.dp, 120.dp)
            .clickable { // handling onMovieClick
                onTableClick(entity)
            },
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp, 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${entity.id}",
                fontSize = 22.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
            Column(
                modifier = Modifier.weight(2F).padding(30.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = entity.id.toString(),
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    maxLines = 1
                )
                Text(
                    text = entity.medieAll.toString(),
                    fontSize = 16.sp
                )
            }
        }
    }
}

