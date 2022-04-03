package com.abhi41.jetweatherforcast.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhi41.jetweatherforcast.data.DataOrException
import com.abhi41.jetweatherforcast.model.Weather
import com.abhi41.jetweatherforcast.navigation.WeatherScreens
import com.abhi41.jetweatherforcast.screens.main.MainViewModel
import com.abhi41.jetweatherforcast.screens.setting.SettingViewModel
import com.abhi41.jetweatherforcast.utils.Constants
import com.abhi41.jetweatherforcast.utils.formatDate
import com.abhi41.jetweatherforcast.utils.formatDecimals
import com.abhi41.jetweatherforcast.widgets.*

private const val TAG = "MainScreen"

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    setttingModel: SettingViewModel = hiltViewModel(),
    city: String?
) {
    val currentCity: String = if (city!!.isBlank()) "Kalyan" else city
    val unitFromDb = setttingModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }
    if (!unitFromDb.isNullOrEmpty()) {
        unit =
            unitFromDb[0].unit.split(" ")[0].lowercase() //from Metric (C) we need only metric in lowercase
        if (unit.equals("imperial")) { //another way is :  isImperial = unit == "isImperial"
            isImperial = true
        }
        val weatherData =
            produceState<DataOrException<Weather, Boolean, Exception>>(
                initialValue = DataOrException(loading = true),
            ) {
                value = mainViewModel.getWeatherData(
                    city = currentCity,
                    unit = unit
                )
            }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            //Text(text = "second ${mainViewModel.getCity()}") // this is my personal techinque to get city
            // Text(text = "MainScreen ${weatherData.data!!.city.country}")

            MainScaffold(weather = weatherData.data!!, navController, isImperial = isImperial)
        }
    }


}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + " ,${weather.city.country}",
            navController = navController,
            elevation = 5.dp,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            }
        ) {
            Log.d(TAG, "MainScaffold: BtnCLicked")
        }
    }) {
        MainContent(data = weather, isImperial = isImperial)
    }

}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {
    val weatherItem = data.list[0]
    val imageUrl = "${Constants.imageUrl}${weatherItem.weather[0].icon}.png"

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp)
        )

        //make big yello circle
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WeatherStateImage(imageUrl = imageUrl)

                Text(
                    text = formatDecimals(weatherItem.temp.day) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic,
                )

            }

        }
        HumidityWindPressureRow(weather = weatherItem, isImperial = isImperial)
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        SunSetSunRiseRow(weatherItem = weatherItem)
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        WeekUpdateRow(data.list)
    }


}


