package com.abhi41.jetweatherforcast.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhi41.jetweatherforcast.model.DbModel.Unit
import com.abhi41.jetweatherforcast.widgets.WeatherAppBar
import okhttp3.internal.wait

@Composable
fun SettingScreen(
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    var unitToggleState by remember {
        mutableStateOf(false)
    }
    val mesurementUnits = listOf("Metric (C)", "Imperial (F)")

    val choiceFromDb = settingViewModel.unitList.collectAsState().value
    //set default value Imperial which in 0th index at mesurementUnits list
    val defaultChoice = if (choiceFromDb.isNullOrEmpty()) {
        mesurementUnits[0]
    } else {
        choiceFromDb[0].unit
    }
    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }


    Scaffold(topBar = {
        WeatherAppBar(
            title = "Setting",
            navController = navController,
            isMainScreen = false,
            icon = Icons.Default.ArrowBack,
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = { unitToggle ->
                        unitToggleState = !unitToggle

                        if (unitToggleState) {
                            choiceState = "Imperial (F)"
                        } else {
                            choiceState = "Metric (C)"
                        }
                    }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = if (unitToggleState) {
                            "Fahrenheit °F"
                        } else {
                            "Celsius °C"
                        },
                    )

                }
                Button(
                    onClick = {
                        settingViewModel.deleteAllUnit()//delete all the previous setting because we need set only one row
                        settingViewModel.insertUnit(
                            Unit(unit = choiceState)
                        )
                    },
                    modifier = Modifier.padding(3.dp),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEFBE42)
                    )
                ) {
                    Text(
                        text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }

        }
    }
}