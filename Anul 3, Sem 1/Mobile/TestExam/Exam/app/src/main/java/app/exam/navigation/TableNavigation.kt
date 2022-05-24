package app.exam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.exam.chooseSectionScreen.ChooseSectionScreen
import app.exam.clientSectionScreen.ClientSectionScreen
import app.exam.navigation.Screen.*
import app.exam.ownerSectionScreen.OwnerSectionScreen
import app.exam.newTableScreen.NewProductScreen

@Composable
fun TableAppNavigation() {
    val navController = rememberNavController()

//    NavHost(navController = navController, startDestination = Warehouses.route){
//        composable(
//            route = Warehouses.route
//        ){
//            OwnerSectionScreen(
//                onSubmit = { selectedWarehouse ->
//                    val warehouseString = Utils.warehouseToString(selectedWarehouse)
//                    val encodedWarehouse = URLEncoder.encode(warehouseString, StandardCharsets.UTF_8.toString())
//                    navController.navigate("${Products.route}/$encodedWarehouse")
//                }
//            )
//        }
//        composable(
//            route = "${Products.route}/{encodedWarehouse}",
//            arguments = listOf(navArgument("encodedWarehouse"){type = NavType.StringType})
//        ) { navBackStackEntry->
//            navBackStackEntry.arguments?.getString("encodedWarehouse")?.let{
//                encodedWarehouse->
//                ProductsScreen(
//                    onProductClick = { selectedProduct ->
//                        //facem produsul String
//                        val productString = Utils.productToString(selectedProduct)
//                        //encoding the product
//                        val encodedProduct = URLEncoder.encode(productString, StandardCharsets.UTF_8.toString())
//                        //navigam la pagina cu datele produsului
//                        navController.navigate("${ProductDetails.route}/$encodedProduct")
//                    },
//                    addProduct = {
//                        //navigam la pagina de adaugare a unui produs
//                        navController.navigate(NewProduct.route)
//                    },
//                    controller = navController,
//                    encodedWarehouse = encodedWarehouse
//                )
//            }
//        }
//        composable(
//            route = "${ProductDetails.route}/{encodedProduct}",
//            arguments = listOf(navArgument("encodedProduct"){type = NavType.StringType})
//        ) { navBackStackEntry ->
//            navBackStackEntry.arguments?.getString("encodedProduct")?.let{
//                encodedProduct->
//                ProductDetailsScreen(controller = navController, encodedProduct = encodedProduct)
//            }
//        }
//        composable(
//            route = NewProduct.route,
//        ) { navBackStackEntry ->
//            NewProductScreen(controller = navController)
//        }
//    }
    NavHost(navController = navController, startDestination = ChooseSection.route) {
        composable(
            route = ChooseSection.route
        ) {
            ChooseSectionScreen(
                onSubmit = { option ->
                    if (option.equals("owner"))
                        navController.navigate(OwnerSection.route)
                    else if (option.equals("client"))
                        navController.navigate(ClientSection.route)
                }
            )
        }
        composable(
            route = OwnerSection.route
        ) { navBackStackEntry ->
            OwnerSectionScreen(
                onProductClick = { selectedTable ->
//                    val tableString = Utils.tableToString(selectedTable)
//                    val encodedTable =
//                        URLEncoder.encode(tableString, StandardCharsets.UTF_8.toString())
//                    navController.navigate("${TableDetails.route}/$encodedTable")
                },
                addProduct = {
                    navController.navigate(NewProduct.route)
                },
                controller = navController
            )
        }
        composable(
            route = ClientSection.route,
        ) { navBackStackEntry ->
            ClientSectionScreen(
                controller = navController
            )
        }
        composable(
            route = NewProduct.route
        ) { navBackStackEntry ->
            NewProductScreen(controller = navController)
        }
//        composable(
//            route = "${TableDetails.route}/{encodedTable}",
//            arguments = listOf(navArgument("encodedTable") { type = NavType.StringType })
//        ) { navBackStackEntry ->
//            navBackStackEntry.arguments?.getString("encodedTable")?.let { encodedTable ->
//                TableDetailsScreen(controller = navController, encodedTable = encodedTable)
//            }
//        }
    }
}