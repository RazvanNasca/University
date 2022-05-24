package app.exam.newTableScreen

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
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.exam.newTableScreen.viewmodel.NewProductViewModel
import app.exam.ownerSectionScreen.domain.Product
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

@Composable
fun NewProductScreen(
    viewModel: NewProductViewModel = hiltViewModel(),
    controller: NavController,
) {
    val name = remember { mutableStateOf("") }
    val type = remember { mutableStateOf("") }
    val quantity = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val discount = remember { mutableStateOf("") }

    //Daca s-au produs modificari in fieldurile cu datele produsului
    val changed = remember { mutableStateOf(false) }
    //Daca se deschide dialog in care se intreaba userul daca doreste sa salveze modificarile
    val openSaveDialog = remember { mutableStateOf(false) }

    //Verificam daca avem date invalide
    val isErrorName = remember { mutableStateOf(false) }
    val isErrorType = remember { mutableStateOf(false) }
    val isErrorQuantity = remember { mutableStateOf(false) }
    val isErrorPrice = remember { mutableStateOf(false) }
    val isErrorDiscount = remember { mutableStateOf(false) }

    //Mesaje de eroare
    var nameErrorString = ""
    var typeErrorString = ""
    var quantityErrorString = ""
    var priceErrorString = ""
    var discountErrorString = ""

    //Daca butonul Save este enabled sau nu, in functie de corectitudinea fieldurilor
    var isEnabled = remember { mutableStateOf(true) }

    //Verificam daca exista eroare
    fun isError(){
        isEnabled.value = !isErrorName.value && !isErrorType.value && !isErrorPrice.value && !isErrorQuantity.value && !isErrorDiscount.value
    }

    //Salvam mesajele de eroare
    viewModel.nameError.observe(LocalLifecycleOwner.current) {
        nameErrorString = it }
    viewModel.typeError.observe(LocalLifecycleOwner.current) {
        typeErrorString = it }
    viewModel.priceError.observe(LocalLifecycleOwner.current) {
        priceErrorString = it   }
    viewModel.discountError.observe(LocalLifecycleOwner.current) {
        discountErrorString = it   }
    viewModel.quantityError.observe(LocalLifecycleOwner.current) {
        quantityErrorString = it   }

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
        isErrorType.value = viewModel.validateType(type.value)
        isErrorPrice.value = viewModel.validatePrice(price.value)
        isErrorDiscount.value = viewModel.validateDiscount(discount.value)
        isErrorQuantity.value = viewModel.validateQuantity(quantity.value)
        isError()
        if (isEnabled.value) {
            val selectedProduct = Product(
                nume = name.value,
                tip = type.value,
                pret = price.value.toInt(),
                cantitate = quantity.value.toInt(),
                discount = discount.value.toInt()
            )
            viewModel.saveProduct(context, selectedProduct, showMessage, progressIndicatorVisibility)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Product") },
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
                                value = type.value,
                                onValueChange = {
                                    type.value = it
                                    changed.value = true
                                    isErrorType.value = viewModel.validateType(type.value)
                                    isError()
                                },
                                label = {
                                    Text(text = "Type")
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    isErrorType.value = viewModel.validateType(type.value)
                                    focusManager.moveFocus(FocusDirection.Down)
                                }),
                                trailingIcon = {
                                    if (isErrorType.value) {
                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                    }
                                }
                            )
                            AssistiveOrErrorText(isError = isErrorType.value, errorString = typeErrorString)
                        }
                    }
                    item {
                        Column() {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = quantity.value,
                                onValueChange = {
                                    quantity.value = it
                                    changed.value = true
                                    isErrorQuantity.value = viewModel.validateQuantity(quantity.value)
                                    isError()
                                },
                                label = {
                                    Text(text = "Quantity")
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    isErrorQuantity.value = viewModel.validateQuantity(quantity.value)
                                    focusManager.moveFocus(FocusDirection.Down)
                                }),
                                trailingIcon = {
                                    if (isErrorQuantity.value) {
                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                    }
                                }
                            )
                            AssistiveOrErrorText(isError = isErrorQuantity.value, errorString = quantityErrorString)
                        }
                    }
                    item {
                        Column() {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = price.value,
                                onValueChange = {
                                    price.value = it
                                    changed.value = true
                                    isErrorPrice.value = viewModel.validatePrice(price.value)
                                    isError()
                                },
                                label = {
                                    Text(text = "Price")
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    isErrorPrice.value = viewModel.validatePrice(price.value)
                                    focusManager.moveFocus(FocusDirection.Down)
                                }),
                                trailingIcon = {
                                    if (isErrorPrice.value) {
                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                    }
                                }
                            )
                            AssistiveOrErrorText(isError = isErrorPrice.value, errorString = priceErrorString)
                        }
                    }
                    item {
                        Column() {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = discount.value,
                                onValueChange = {
                                    discount.value = it
                                    changed.value = true
                                    isErrorDiscount.value = viewModel.validateDiscount(discount.value)
                                    isError()
                                },
                                label = {
                                    Text(text = "Discount")
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    isErrorDiscount.value = viewModel.validateDiscount(discount.value)
                                    focusManager.moveFocus(FocusDirection.Down)
                                }),
                                trailingIcon = {
                                    if (isErrorDiscount.value) {
                                        Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colors.error)
                                    }
                                }
                            )
                            AssistiveOrErrorText(isError = isErrorDiscount.value, errorString = discountErrorString)
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
            color = Color.Gray,
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