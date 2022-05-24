package com.example.exam3.newTableScreen

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
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
import com.example.exam3.newTableScreen.viewmodel.NewProductViewModel
import com.example.exam3.ownerSectionScreen.domain.Rezervare
import kotlinx.coroutines.launch

@Composable
fun NewProductScreen(
    viewModel: NewProductViewModel = hiltViewModel(),
    controller: NavController,
) {
    val name = remember { mutableStateOf("") }
    val doctor = remember { mutableStateOf("") }
    val data = remember { mutableStateOf("") }
    val ora = remember { mutableStateOf("") }
    val detalii = remember { mutableStateOf("") }

    //Daca s-au produs modificari in fieldurile cu datele produsului
    val changed = remember { mutableStateOf(false) }
    //Daca se deschide dialog in care se intreaba userul daca doreste sa salveze modificarile
    val openSaveDialog = remember { mutableStateOf(false) }

    //Verificam daca avem date invalide
    val isErrorName = remember { mutableStateOf(false) }
    val isErrorDoctor = remember { mutableStateOf(false) }
    val isErrorData = remember { mutableStateOf(false) }
    val isErrorOra = remember { mutableStateOf(false) }
    val isErrorDetalii = remember { mutableStateOf(false) }

    //Mesaje de eroare
    var nameErrorString = ""
    var doctorErrorString = ""
    var dataErrorString = ""
    var oraErrorString = ""
    var detaliiErrorString = ""

    //Daca butonul Save este enabled sau nu, in functie de corectitudinea fieldurilor
    var isEnabled = remember { mutableStateOf(true) }

    //Verificam daca exista eroare
    fun isError(){
        isEnabled.value = !isErrorName.value && !isErrorDoctor.value && !isErrorOra.value && !isErrorData.value && !isErrorDetalii.value
    }

    //Salvam mesajele de eroare
    viewModel.nameError.observe(LocalLifecycleOwner.current) {
        nameErrorString = it }
    viewModel.doctorError.observe(LocalLifecycleOwner.current) {
        doctorErrorString = it }
    viewModel.oraError.observe(LocalLifecycleOwner.current) {
        oraErrorString = it   }
    viewModel.detaliiError.observe(LocalLifecycleOwner.current) {
        detaliiErrorString = it   }
    viewModel.dataError.observe(LocalLifecycleOwner.current) {
        dataErrorString = it   }

    val context = LocalContext.current

    val progressIndicatorVisibility = remember{ mutableStateOf(false) }

    val snackbarHostState = remember{mutableStateOf(SnackbarHostState())}
    val snackbarCoroutineScope = rememberCoroutineScope()

    val showMessage: (Boolean, String) -> Unit = { isSuccessful: Boolean, message: String ->
        if(isSuccessful){
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_LONG
                ).show()
                controller.popBackStack()
            }
        } else {
            snackbarCoroutineScope.launch {
                snackbarHostState.value.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    val focusManager = LocalFocusManager.current

    fun trySaveProduct(){
        isErrorName.value = viewModel.validateName(name.value)
        isErrorDoctor.value = viewModel.validateDoctor(doctor.value)
        isErrorOra.value = viewModel.validateOra(ora.value)
        isErrorDetalii.value = viewModel.validateDiscount(detalii.value)
        isErrorData.value = viewModel.validateData(data.value)
        isError()
        if (isEnabled.value) {
            val selectedProduct = Rezervare(
                nume = name.value,
                doctor = doctor.value,
                ora = ora.value.toInt(),
                data = data.value.toInt(),
                detalii = detalii.value
            )
            viewModel.saveProduct(context, selectedProduct, showMessage, progressIndicatorVisibility)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Rezervare") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (!changed.value || !isEnabled.value) //Daca nu s-a modificat nimic sau butonul de Save nu este enabled
                            controller.popBackStack()
                        else
                            openSaveDialog.value = true //deschidem fereastra dialog
                    }) {
                        Icon(Icons.Filled.ArrowBack,null)
                    }
                }
            )
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                if(progressIndicatorVisibility.value){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center).size(100.dp)
                    )
                }
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(50.dp, 20.dp).align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Column() {
                            OutlinedTextField(
                                value = name.value,
                                onValueChange = {
                                    name.value = it
                                    changed.value = true
                                    isErrorName.value = viewModel.validateName(name.value)
                                    isError()
                                },
                                label = {
                                    Text(text = "Name")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    isErrorName.value = viewModel.validateName(name.value)
                                    focusManager.moveFocus(FocusDirection.Down)
                                }),
                                trailingIcon = {
                                    if (isErrorName.value) {
                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                    }
                                }
                            )
                            AssistiveOrErrorText(isError = isErrorName.value, errorString = nameErrorString)
                        }
                    }
                    item {
                        Column() {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = doctor.value,
                                onValueChange = {
                                    doctor.value = it
                                    changed.value = true
                                    isErrorDoctor.value = viewModel.validateDoctor(doctor.value)
                                    isError()
                                },
                                label = {
                                    Text(text = "Doctor")
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    isErrorDoctor.value = viewModel.validateDoctor(doctor.value)
                                    focusManager.moveFocus(FocusDirection.Down)
                                }),
                                trailingIcon = {
                                    if (isErrorDoctor.value) {
                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                    }
                                }
                            )
                            AssistiveOrErrorText(isError = isErrorDoctor.value, errorString = doctorErrorString)
                        }
                    }
                    item {
                        Column() {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = data.value,
                                onValueChange = {
                                    data.value = it
                                    changed.value = true
                                    isErrorData.value = viewModel.validateData(data.value)
                                    isError()
                                },
                                label = {
                                    Text(text = "Data")
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    isErrorData.value = viewModel.validateData(data.value)
                                    focusManager.moveFocus(FocusDirection.Down)
                                }),
                                trailingIcon = {
                                    if (isErrorData.value) {
                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                    }
                                }
                            )
                            AssistiveOrErrorText(isError = isErrorData.value, errorString = dataErrorString)
                        }
                    }
                    item {
                        Column() {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = ora.value,
                                onValueChange = {
                                    ora.value = it
                                    changed.value = true
                                    isErrorOra.value = viewModel.validateOra(ora.value)
                                    isError()
                                },
                                label = {
                                    Text(text = "Ora")
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    isErrorOra.value = viewModel.validateOra(ora.value)
                                    focusManager.moveFocus(FocusDirection.Down)
                                }),
                                trailingIcon = {
                                    if (isErrorOra.value) {
                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                    }
                                }
                            )
                            AssistiveOrErrorText(isError = isErrorOra.value, errorString = oraErrorString)
                        }
                    }
                    item {
                        Column() {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = detalii.value,
                                onValueChange = {
                                    detalii.value = it
                                    changed.value = true
                                    isErrorDetalii.value = viewModel.validateDiscount(detalii.value)
                                    isError()
                                },
                                label = {
                                    Text(text = "Detalii")
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    isErrorDetalii.value = viewModel.validateDiscount(detalii.value)
                                    focusManager.moveFocus(FocusDirection.Down)
                                }),
                                trailingIcon = {
                                    if (isErrorDetalii.value) {
                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                    }
                                }
                            )
                            AssistiveOrErrorText(isError = isErrorDetalii.value, errorString = detaliiErrorString)
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                content = {
                                    Text(text = "Cancel")
                                },
                                onClick = {
                                    if (!changed.value || !isEnabled.value) //Daca nu s-a modificat nimic sau butonul de Save nu este enabled
                                        controller.popBackStack()
                                    else
                                        openSaveDialog.value = true //deschidem fereastra dialog
                                }
                            )
                            Button(
                                enabled = isEnabled.value,
                                content = {
                                    Text(text = "Save")
                                },
                                onClick = {
                                    trySaveProduct()
                                }
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
                        modifier = Modifier.padding(8.dp),
                    ) { Text(text = it.message) }
                }
            )
        }
    )

    if (openSaveDialog.value && changed.value) {
        val context = LocalContext.current
        AlertDialog(
            onDismissRequest = {
                openSaveDialog.value = false
            },
            title = {
                Text(text = "Do you wish to save?")
            },
            dismissButton = {
                Button(
                    onClick = {
                        openSaveDialog.value = false
                        changed.value = false
                        controller.popBackStack()
                    }) {
                    Text("No")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openSaveDialog.value = false
                        trySaveProduct()
                    }) {
                    Text("Yes")
                }
            }
        )
    }
}
//
@Composable
fun AssistiveOrErrorText(
    isError: Boolean,
    errorString: String
) {
    if (isError) {
        Text(
            text = errorString,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    } else {
        Text(
            text = "Required",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

//@Composable
//fun DropDown(
//    optionsList: List<TableType>,
//    updateTableType: (type: TableType) -> Unit
//) {
//
//    var expanded by remember { mutableStateOf(false) }
//
//    var selectedWarehouse: TableType by remember { mutableStateOf(optionsList[0]) }
//
//    var textfieldSize by remember { mutableStateOf(Size.Zero) }
//
//    val icon = if (expanded)
//        Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
//    else
//        Icons.Filled.ArrowDropDown
//
//
//    Column {
//        OutlinedTextField(
//            value = selectedWarehouse.value ?: "",
//            onValueChange = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates ->
//                    //This value is used to assign to the DropDown the same width
//                    textfieldSize = coordinates.size.toSize()
//                }
//                .clickable { expanded = !expanded },
//            label = { Text("Choose warehouse") },
//            trailingIcon = {
//                Icon(icon, null)
//            },
//            enabled = false,
//            colors = TextFieldDefaults.textFieldColors(
//                disabledIndicatorColor = Color.Black,
//                disabledLabelColor = Color.Black,
//                disabledTextColor = Color.Black,
//                disabledTrailingIconColor = Color.Black
//            )
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier
//                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
//        ) {
//            optionsList.forEach { warehouse ->
//                DropdownMenuItem(onClick = {
//                    selectedWarehouse = warehouse
//                    updateTableType(selectedWarehouse!!)
//                    expanded = false
//                }) {
//                    Text(text = warehouse.value)
//                }
//            }
//        }
//    }
//}