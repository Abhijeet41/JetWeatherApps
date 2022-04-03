package com.abhi41.jetweatherforcast.repository

import android.util.Log
import com.abhi41.jetweatherforcast.data.DataOrException
import com.abhi41.jetweatherforcast.model.Weather
import com.abhi41.jetweatherforcast.model.WeatherObject
import com.abhi41.jetweatherforcast.network.WeatherApi
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "WeatherRepository"

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(
        cityQuery: String,
        unit: String,
    ): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(cityQuery, units = unit)
        } catch (e: Exception) {
            Log.d(TAG, "Exception: $e")
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }

}