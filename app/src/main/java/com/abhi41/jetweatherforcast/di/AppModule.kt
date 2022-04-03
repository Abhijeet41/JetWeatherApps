package com.abhi41.jetweatherforcast.di

import android.content.Context
import androidx.room.Room
import com.abhi41.jetweatherforcast.data.WeatherDao
import com.abhi41.jetweatherforcast.data.WeatherDatabase
import com.abhi41.jetweatherforcast.network.WeatherApi
import com.abhi41.jetweatherforcast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//In this class we create provide where it allows for that dependency injection

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }


    @Singleton //provide singleton database
    @Provides  // we need context it provide by hint lib
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            Constants.weatherDB // weather database named
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides //provide dao class
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.weatherDao()
    }


}