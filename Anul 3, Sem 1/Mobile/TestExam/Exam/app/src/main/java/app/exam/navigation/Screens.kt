package app.exam.navigation

sealed class Screen(val route: String){
    object OwnerSection : Screen(route = "owner")
    object ClientSection : Screen(route = "client")
    object ChooseSection : Screen(route = "chooseSection")
    object NewProduct: Screen(route = "newTable")
}