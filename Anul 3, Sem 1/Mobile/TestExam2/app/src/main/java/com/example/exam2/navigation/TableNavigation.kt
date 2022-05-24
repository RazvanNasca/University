package com.example.exam2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exam2.clientSectionScreen.ClientSectionScreen
import com.example.exam2.navigation.Screen.*
import com.example.exam2.ownerSectionScreen.OwnerSectionScreen
import com.example.exam2.newTableScreen.NewProductScreen
import com.example.exam2.chooseSectionScreen.ChooseSectionScreen

@Composable
fun TableAppNavigation() {
    val navController = rememberNavController()

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
                    navController.navigate(NewTable.route)
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
            route = NewTable.route
        ) { navBackStackEntry ->
            NewProductScreen(controller = navController)
        }

    }
}