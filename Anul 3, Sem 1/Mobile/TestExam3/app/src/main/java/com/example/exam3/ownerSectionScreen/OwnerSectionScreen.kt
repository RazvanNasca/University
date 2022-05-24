package com.example.exam3.ownerSectionScreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.exam3.ownerSectionScreen.domain.Rezervare
import com.example.exam3.ownerSectionScreen.viewmodel.OwnerSectionViewModel
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch

internal var shouldGetListFromDatabase = true

@Composable
fun OwnerSectionScreen(
    viewModel: OwnerSectionViewModel = hiltViewModel(),
    onRezervareClick: (Rezervare) -> Unit,
    controller: NavController
) {
    val listOfRezervari by viewModel.listOfRezervari.observeAsState(listOf())

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
        viewModel.loadRezervari(context, showError, progressIndicatorVisibility)
        shouldGetListFromDatabase = false
    }

    viewModel.isRetrievedSuccessfully.observe(LocalLifecycleOwner.current) {
        if (it == false) {
            Toast.makeText(
                context,
                "There was a problem in retrieving the Rezeravri!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    BackHandler {
        shouldGetListFromDatabase = true
        controller.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Secretar Section") },
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
                        items(listOfRezervari) { item ->
                            SingleTableItem(
                                rezervare = item,
                                onRezervareClick = onRezervareClick
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
                            if (listOfRezervari.isEmpty()) {
                                Button(onClick = {
                                    snackbarHostState.value.currentSnackbarData?.dismiss()
                                    viewModel.loadRezervari(
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SingleTableItem(
    rezervare: Rezervare,
    onRezervareClick: (Rezervare) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .size(0.dp, 120.dp)
            .clickable { // handling onMovieClick
                onRezervareClick(rezervare)
            },
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp, 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${rezervare.id}",
                fontSize = 22.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
            Column(
                modifier = Modifier.weight(2F)
            ) {
                Text(
                    text = rezervare.nume,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    maxLines = 1
                )
                Text(
                    modifier = Modifier.padding(0.dp, 8.dp),
                    text = rezervare.doctor,
                    fontSize = 16.sp
                )
                Text(
                    text = rezervare.data.toString(),
                    fontSize = 16.sp
                )
                Text(
                    text = rezervare.ora.toString(),
                    fontSize = 16.sp
                )
            }
        }
    }
}