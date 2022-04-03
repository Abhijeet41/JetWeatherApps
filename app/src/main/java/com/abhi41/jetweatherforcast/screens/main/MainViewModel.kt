package com.abhi41.jetweatherforcast.screens.main

import androidx.lifecycle.ViewModel
import com.abhi41.jetweatherforcast.data.DataOrException
import com.abhi41.jetweatherforcast.model.Weather
import com.abhi41.jetweatherforcast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(private val respository: WeatherRepository) : ViewModel() {
    //this is my personal technique to get city
       private var dataOrException = DataOrException<Weather,
               Boolean,
               Exception>()

    suspend fun getWeatherData(city: String, unit: String)
            : DataOrException<Weather, Boolean, Exception> {
        // dataOrException =respository.getWeather(cityQuery = city) this is my personal technique to get city
        return respository.getWeather(cityQuery = city,unit = unit)
    }

    //this is my personal technique to get city
     fun getCity ():String{
        return dataOrException.data?.city!!.country
    }

}