package com.abhi41.jetweatherforcast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhi41.jetweatherforcast.model.DbModel.Favorite
import com.abhi41.jetweatherforcast.navigation.WeatherScreens
import com.abhi41.jetweatherforcast.screens.favorite.FavoriteViewModel

//@Preview
@Composable
fun WeatherAppBar(
    title: String = "title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(), //because of hilt viewmodel we don't need to pass it from main screen
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {

    }
) {

    val showDialog = remember { //every time working on dialog we need this mutable state
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }
    //show it for toast message
    val showIt = remember {
        mutableStateOf<Boolean>(false)
    }

    val context = LocalContext.current

    TopAppBar(title = {
        Text(
            text = title, color = MaterialTheme.colors.onSecondary,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
        )
    }, actions = {
        if (isMainScreen) {
            IconButton(onClick = {
                onAddActionClicked.invoke()
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
            }
            IconButton(onClick = {
                showDialog.value = true
            }) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "MoreVert icon")
            }
        } else Box {
            //if its not main screen don't show search and more option
        }
        showToast(context = context, showIt = showIt.value)
    },
        navigationIcon = {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    })
            }
            if (isMainScreen) {
                val isAlreadyfav = favoriteViewModel.favList
                    .collectAsState().value
                    .filter { item ->
                        (item.city == title.split(",")[0])
                    }

                if (isAlreadyfav.isNullOrEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val dataList = title.split(",")
                                favoriteViewModel
                                    .insertfavorite(
                                        Favorite(
                                            city = dataList[0], //city name
                                            country = dataList[1] //country name
                                        )
                                    )
                                    .run {
                                        showIt.value = true // this for display toast message
                                    }

                            },
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                } else {
                    Box() {
                        //show nothing
                        showIt.value = false //set to false for toast message
                    }
                }

            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )

}

fun showToast(context: Context, showIt: Boolean) {
    if (showIt) {
        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {
    var expanded by remember {
        mutableStateOf(true)
    }

    /* var expanded = remember { we can also write like this
         mutableStateOf(true)
     }*/
    val itesm = listOf("About", "Favorite", "Setting")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White),

            ) {

            itesm.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        showDialog.value = false
                    },
                ) {

                    Row(modifier = Modifier
                        .padding(0.dp)
                        .clickable {
                            navController.navigate(
                                when (text) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favorite" -> WeatherScreens.FavoriteScreen.name
                                    else -> {
                                        WeatherScreens.SettingScreen.name
                                    }
                                }
                            )
                        }) {

                        Icon(
                            imageVector = when (text) {
                                "About" -> Icons.Default.Info
                                "Favorite" -> Icons.Default.FavoriteBorder
                                else -> {
                                    Icons.Default.Settings
                                }
                            },
                            contentDescription = null,
                            tint = Color.LightGray
                        )
                        Text(text = text, modifier = Modifier.clickable {
                            navController.navigate(
                                when (text) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favorite" -> WeatherScreens.FavoriteScreen.name
                                    else -> {
                                        WeatherScreens.SettingScreen.name
                                    }
                                }
                            )
                        }, fontWeight = FontWeight.W300)
                    }

                }
            }
        }
    }
}
