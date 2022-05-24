package com.example.exam3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exam3.clientSectionScreen.ClientSectionScreen
import com.example.exam3.navigation.Screen.*
import com.example.exam3.ownerSectionScreen.OwnerSectionScreen
import com.example.exam3.newTableScreen.NewProductScreen
import com.example.exam3.chooseSectionScreen.ChooseSectionScreen

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
                    else if (option.equals("newTable"))
                        navController.navigate(NewTable.route)
                }
            )
        }
        composable(
            route = OwnerSection.route
        ) { navBackStackEntry ->
            OwnerSectionScreen(
                onRezervareClick = { selectedTable ->
//                    val tableString = Utils.tableToString(selectedTable)
//                    val encodedTable =
//                        URLEncoder.encode(tableString, StandardCharsets.UTF_8.toString())
//                    navController.navigate("${TableDetails.route}/$encodedTable")
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