package com.example.examenma.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.examenma.clientSectionScreen.ClientSectionScreen
import com.example.examenma.navigation.Screen.*
import com.example.examenma.newEntityScreen.NewEntityScreen
import com.example.examenma.chooseSectionScreen.ChooseSectionScreen
import com.example.examenma.ownerSectionScreen.OwnerSectionScreen

@Composable
fun AppNavigation() {
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
                onEntityClick = { selectedTable ->
//                    val tableString = Utils.tableToString(selectedTable)
//                    val encodedTable =
//                        URLEncoder.encode(tableString, StandardCharsets.UTF_8.toString())
//                    navController.navigate("${TableDetails.route}/$encodedTable")
                },
                addEntity = {
                    navController.navigate(NewEntity.route)
                },
                controller = navController
            )
        }
        composable(
            route = ClientSection.route,
        ) {
            ClientSectionScreen(
                controller = navController
            )
        }
        composable(
            route = NewEntity.route
        ) {
            NewEntityScreen(controller = navController)
        }

    }
}