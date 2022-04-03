package com.abhi41.jetweatherforcast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abhi41.jetweatherforcast.screens.MainScreen
import com.abhi41.jetweatherforcast.screens.WeatherSplashScreen
import com.abhi41.jetweatherforcast.screens.about.AboutScreen
import com.abhi41.jetweatherforcast.screens.favorite.FavouriteScreen
import com.abhi41.jetweatherforcast.screens.main.MainViewModel
import com.abhi41.jetweatherforcast.screens.search.SearchScreen
import com.abhi41.jetweatherforcast.screens.setting.SettingScreen

@Composable
fun WeatherNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name,
    ) {

        composable(route = WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable(
            "$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel =
                    hiltViewModel<MainViewModel>() //calling viewmodel from mainscreen
                MainScreen(navController = navController, mainViewModel,city = city)
            }

        }

        composable(route = WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(route = WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        composable(route = WeatherScreens.FavoriteScreen.name) {
            FavouriteScreen(navController = navController)
        }
        composable(route = WeatherScreens.SettingScreen.name) {
            SettingScreen(navController = navController)
        }

    }
}