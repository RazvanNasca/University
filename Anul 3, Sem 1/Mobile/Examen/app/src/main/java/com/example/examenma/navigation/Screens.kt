package com.example.examenma.navigation

sealed class Screen(val route: String){
    object OwnerSection : Screen(route = "owner")
    object ClientSection : Screen(route = "client")
    object ChooseSection : Screen(route = "chooseSection")
    object NewEntity: Screen(route = "newEntity")
}