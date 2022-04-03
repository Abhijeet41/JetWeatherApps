package com.abhi41.jetweatherforcast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.abhi41.jetweatherforcast.R
import com.abhi41.jetweatherforcast.model.WeatherItem
import com.abhi41.jetweatherforcast.utils.Constants
import com.abhi41.jetweatherforcast.utils.formatDate
import com.abhi41.jetweatherforcast.utils.formatDateTime
import com.abhi41.jetweatherforcast.utils.formatDecimals


@Composable
fun WeekUpdateRow(listWeather: List<WeatherItem>) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color(0xFFEEF1EF),
        shape = RoundedCornerShape(size = 14.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            items(listWeather) { item ->
                RowWeatherData(
                    item.dt,
                    item.weather[0].icon,
                    item.weather[0].description,
                    item.temp.max,
                    item.temp.min
                )
            }
        }
    }


}

@Composable
fun RowWeatherData(dt: Int, icon: String, description: String, max: Double, min: Double) {
    val imageUrl = "${Constants.imageUrl}${icon}.png"

    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        color = Color.White,
        shape = CircleShape.copy(topEnd = CornerSize(6.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            //we use split because we want only Sun not Sun, Mar 20
            Text(
                text = formatDate(dt).split(",")[0],
                modifier = Modifier.padding(start = 5.dp)
            )
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "icon image",
                modifier = Modifier.size(80.dp)
            )
            Surface(
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(
                    text = description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Row() {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Blue.copy(0.7f),
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(formatDecimals(max) + "°")
                        }

                        withStyle(
                            style = SpanStyle(
                                color = Color.Gray.copy(0.7f),
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(formatDecimals(min) + "°")
                        }
                    },

                    )

            }

        }
    }


}

@Composable
fun SunSetSunRiseRow(weatherItem: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weatherItem.sunrise),
                style = MaterialTheme.typography.caption
            )
        }
        Row() {
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weatherItem.sunset),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {

    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding()) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.caption
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.pressure}%",
                style = MaterialTheme.typography.caption
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${formatDecimals(weather.speed)}" + if (isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption
            )
        }
    }

}


@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "icon image",
        modifier = Modifier.size(80.dp)
    )
}

