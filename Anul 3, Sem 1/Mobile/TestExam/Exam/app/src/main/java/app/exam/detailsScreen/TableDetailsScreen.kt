package app.gestionareproduse.detailsScreen

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.exam.R
import app.exam.detailsScreen.viewmodel.TableDetailsViewModel
import app.exam.utils.DatePickerview
import app.exam.utils.Utils
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun TableDetailsScreen(
    viewModel: TableDetailsViewModel = hiltViewModel(),
    controller: NavController,
    encodedTable : String
){
//    val tableString = URLDecoder.decode(encodedTable, StandardCharsets.UTF_8.toString())
//    var selectedTable = Utils.stringToTable(tableString)
//
//    //Date produs
//    val name = remember{ mutableStateOf(selectedTable.name)}
//    val seats = remember{ mutableStateOf(selectedTable.seats.toString())}
//    val type = remember{ mutableStateOf(selectedTable.type)}
//
//    val updateType = { newType: TableType ->
//        type.value = newType
//    }
//
//    val openDeleteDialog = remember { mutableStateOf(false)  }
//    val openUpdateDialog = remember { mutableStateOf(false)}
//    val changed = remember { mutableStateOf(false)}
//
//    val isErrorName = remember { mutableStateOf(false) }
//    val isErrorSeats = remember { mutableStateOf(false) }
//
//    var nameErrorString = ""
//    var seatsErrorString = ""
//
//    var isEnabled = remember { mutableStateOf(true) }
//
//    fun isError(){
//        isEnabled.value = !isErrorName.value && !isErrorSeats.value
//    }
//
//    viewModel.nameError.observe(LocalLifecycleOwner.current) {
//        nameErrorString = it }
//    viewModel.seatsError.observe(LocalLifecycleOwner.current) {
//        seatsErrorString = it }
//
//    val focusManager = LocalFocusManager.current
//
//    val context = LocalContext.current
//
//    val progressIndicatorVisibility = remember{ mutableStateOf(false) }
//
//    val snackbarHostState = remember{mutableStateOf(SnackbarHostState())}
//    val snackbarCoroutineScope = rememberCoroutineScope()
//
//    val showMessage: (Boolean, String) -> Unit = { isSuccessful: Boolean, message: String ->
//        if(isSuccessful){
//            Handler(Looper.getMainLooper()).post{
//                Toast.makeText(
//                    context,
//                    message,
//                    Toast.LENGTH_LONG
//                ).show()
//                controller.popBackStack()
//            }
//        } else {
//            snackbarCoroutineScope.launch {
//                snackbarHostState.value.showSnackbar(
//                    message = message,
//                    duration = SnackbarDuration.Short
//                )
//            }
//        }
//    }
//
//    fun tryUpdateTable(){
//        isErrorName.value = viewModel.validateName(name.value)
//        isErrorSeats.value = viewModel.validateSeats(seats.value)
//        isError()
//        if(isEnabled.value) {
//            selectedTable.name = name.value
//            selectedTable.seats = seats.value.toInt()
//            selectedTable.type = type.value
//            viewModel.updateTable(context, selectedTable, showMessage, progressIndicatorVisibility)
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("View Table") },
//                navigationIcon = {
//                    IconButton(onClick = {
//                        if (!changed.value || !isEnabled.value)
//                            controller.popBackStack()
//                        else
//                            openUpdateDialog.value = true
//                    }) {
//                        Icon(Icons.Filled.ArrowBack,null)
//                    }
//                }
//            )
//        },
//        content = {
//            Box(
//                modifier = Modifier.fillMaxSize()
//            ){
//                if(progressIndicatorVisibility.value){
//                    CircularProgressIndicator(
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .size(100.dp)
//                    )
//                }
//                LazyColumn(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .padding(50.dp, 20.dp)
//                        .align(Alignment.Center),
//                    verticalArrangement = Arrangement.spacedBy(20.dp)
//                ){
//                    item {
//                        Column(){
//                            OutlinedTextField(
//                                value = name.value,
//                                onValueChange = {
//                                    name.value = it
//                                    changed.value = true
//                                    isErrorName.value = viewModel.validateName(name.value)
//                                    isError()
//                                },
//                                label = {
//                                    Text(text = "Name")
//                                },
//                                modifier = Modifier.fillMaxWidth(),
//                                keyboardOptions = KeyboardOptions.Default.copy(
//                                    keyboardType = KeyboardType.Text,
//                                    imeAction = ImeAction.Next
//                                ),
//                                keyboardActions = KeyboardActions(onNext = {
//                                    isErrorName.value = viewModel.validateName(name.value)
//                                    focusManager.moveFocus(FocusDirection.Down)
//                                }),
//                                trailingIcon = {
//                                    if (isErrorName.value) {
//                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
//                                    }
//                                }
//                            )
//                            assistiveOrErrorText(isError = isErrorName.value, errorString = nameErrorString)
//                        }
//                    }
//                    item{
//                        Column() {
//                            OutlinedTextField(
//                                modifier = Modifier.fillMaxWidth(),
//                                value = seats.value,
//                                onValueChange = {
//                                    seats.value = it
//                                    changed.value = true
//                                    isErrorSeats.value = viewModel.validateSeats(seats.value)
//                                    isError()
//                                },
//                                label = {
//                                    Text(text = "seats")
//                                },
//                                keyboardOptions = KeyboardOptions.Default.copy(
//                                    keyboardType = KeyboardType.Number,
//                                    imeAction = ImeAction.Next
//                                ),
//                                keyboardActions = KeyboardActions(onNext = {
//                                    isErrorSeats.value = viewModel.validateSeats(seats.value)
//                                    focusManager.moveFocus(FocusDirection.Down)
//                                }),
//                                trailingIcon = {
//                                    if (isErrorSeats.value) {
//                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
//                                    }
//                                }
//                            )
//                            assistiveOrErrorText(isError = isErrorSeats.value, errorString = seatsErrorString)
//                        }
//                    }
//                    item{
//                        DropDown(optionsList = getAllTableTypes(), updateTableType = updateType)
//                    }
//                    item{
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ){
//                            Button(
//                                content = {
//                                    Text(text = "Cancel")
//                                },
//                                onClick = {
//                                    if (!changed.value || !isEnabled.value)
//                                        controller.popBackStack()
//                                    else
//                                        openUpdateDialog.value = true
//                                }
//                            )
//
//                            val context = LocalContext.current
//                            Button(
//                                enabled = isEnabled.value,
//                                content = {
//                                    Text(text = "Update")
//                                },
//                                onClick = {
//                                    tryUpdateTable()
//                                }
//                            )
//                            Button(
//                                content = {
//                                    Text(text = "Delete")
//                                },
//                                onClick = {
//                                    openDeleteDialog.value = true
//                                }
//                            )
//                        }
//                    }
//
//                }
//            }
//        },
//        snackbarHost = {
//            SnackbarHost(
//                hostState = snackbarHostState.value,
//                snackbar = {
//                    Snackbar(
//                        modifier = Modifier.padding(8.dp),
//                    ) { Text(text = it.message) }
//                }
//            )
//        }
//    )
//
//    if(openUpdateDialog.value && changed.value){
//        val context = LocalContext.current
//        AlertDialog(
//            onDismissRequest = {
//                openUpdateDialog.value = false
//            },
//            title = {
//                Text(text = "Do you wish to save the modifications?")
//            },
//            dismissButton = {
//                Button(
//                    onClick = {
//                        openUpdateDialog.value = false
//                        changed.value = false
//                        controller.popBackStack()
//                    }) {
//                    Text("No")
//                }
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        openUpdateDialog.value = false
//                        tryUpdateTable()
//                    }) {
//                    Text("Yes")
//                }
//            }
//        )
//    }
//
//    if (openDeleteDialog.value) {
//        val context = LocalContext.current
//        AlertDialog(
//            onDismissRequest = {
//                openDeleteDialog.value = false
//            },
//            title = {
//                Text(text = "Do you wish to delete the Table?")
//            },
//            dismissButton = {
//                Button(
//                    onClick = {
//                        openDeleteDialog.value = false
//                    }) {
//                    Text("No")
//                }
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        openDeleteDialog.value = false
//                        selectedTable.id?.let { viewModel.deleteTable(context, it, showMessage, progressIndicatorVisibility) }
//                    }) {
//                    Text("Yes")
//                }
//            }
//        )
//    }
}
