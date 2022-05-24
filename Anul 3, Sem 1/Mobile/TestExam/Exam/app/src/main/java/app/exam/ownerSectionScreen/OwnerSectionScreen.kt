package app.exam.ownerSectionScreen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.exam.ownerSectionScreen.domain.Product
import app.exam.ownerSectionScreen.viewmodel.OwnerSectionViewModel
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch

internal var shouldGetListFromDatabase = true

@Composable
fun OwnerSectionScreen(
    viewModel: OwnerSectionViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit,
    addProduct: () -> Unit,
    controller: NavController
) {
    val listOfProducts by viewModel.listOfProducts.observeAsState(listOf())

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

    if (app.exam.clientSectionScreen.shouldGetListFromDatabase) {
        viewModel.loadProducts(context, showError, progressIndicatorVisibility)
        app.exam.clientSectionScreen.shouldGetListFromDatabase = false
    }

    viewModel.isRetrievedSuccessfully.observe(LocalLifecycleOwner.current) {
        if (it == false) {
            Toast.makeText(
                context,
                "There was a problem in retrieving the Products!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    BackHandler {
        app.exam.clientSectionScreen.shouldGetListFromDatabase = true
        controller.popBackStack()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addProduct() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Owner Section") },
                navigationIcon = {
                    IconButton(onClick = {
                        app.exam.clientSectionScreen.shouldGetListFromDatabase = true
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
                        items(listOfProducts) { item ->
                            app.exam.clientSectionScreen.SingleTableItem(
                                product = item,
                                onTableClick = onProductClick
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
                            if (listOfProducts.isEmpty()) {
                                Button(onClick = {
                                    snackbarHostState.value.currentSnackbarData?.dismiss()
                                    viewModel.loadProducts(
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
    product: Product = Product(id = 13, nume = "Test", tip = "Tip1", cantitate = 100, pret = 69, discount = 50),
    onTableClick: (Product) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .size(0.dp, 120.dp)
            .clickable { // handling onMovieClick
                onTableClick(product)
            },
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp, 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${product.id}",
                fontSize = 22.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
            Column(
                modifier = Modifier.weight(2F).padding(30.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = product.nume,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    maxLines = 1
                )
                Text(
                    modifier = Modifier.padding(0.dp, 8.dp),
                    text = product.tip,
                    fontSize = 16.sp
                )
                Text(
                    text = product.cantitate.toString(),
                    fontSize = 16.sp
                )
            }
            Text(
                text = "${product.pret} RON",
                fontSize = 22.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun Chip(
//    name: String = "Chip",
//    isSelected: Boolean = false,
//    onSelectionChanged: (String) -> Unit = {}
//) {
//    Surface(
//        modifier = Modifier.padding(4.dp),
//        shape = RoundedCornerShape(16.dp),
//        color = if (isSelected) Color(0xFFD3D3D3) else Color(0x1F232F34),
//
//        ) {
//        Row(
//            modifier = Modifier.toggleable(
//                value = isSelected,
//                onValueChange = {
//                    onSelectionChanged(name)
//                }
//            )
//        ) {
//            if (isSelected) {
//                Icon(
//                    imageVector = Icons.Filled.Done,
//                    contentDescription = null,
//                    tint = Color(0xFF252525),
//                    modifier = Modifier
//                        .size(25.dp, 20.dp)
//                        .align(Alignment.CenterVertically)
//                        .padding(5.dp, 0.dp, 0.dp, 0.dp),
//                )
//            }
//            Text(
//                text = name,
//                style = MaterialTheme.typography.body2,
//                color = if (isSelected) Color(0xFF252525) else Color(0xFF232F34),
//                modifier = Modifier.padding(10.dp, 5.dp)
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ChipGroup(
//    sortFields: List<SortField> = getAllSortFields(),
//    selectedField: SortField? = null,
//    onSelectionChanged: (String) -> Unit = {}
//) {
//    Column(modifier = Modifier.padding(8.dp, 8.dp, 0.dp, 0.dp)) {
//        LazyRow {
//            items(sortFields) {
//                Chip(
//                    name = it.value,
//                    isSelected = selectedField == it,
//                    onSelectionChanged = {
//                        onSelectionChanged(it)
//                    }
//                )
//            }
//        }
//    }
//}