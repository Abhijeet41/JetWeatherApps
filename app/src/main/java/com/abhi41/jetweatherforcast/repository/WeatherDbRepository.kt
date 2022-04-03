package com.abhi41.jetweatherforcast.repository

import com.abhi41.jetweatherforcast.data.WeatherDao
import com.abhi41.jetweatherforcast.model.DbModel.Favorite
import com.abhi41.jetweatherforcast.model.DbModel.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFevorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavourite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavourite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorites(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavourites()
    suspend fun getFavById(city: String): Favorite = weatherDao.getFavById(city)

    //---------------------Unit--------------------------
    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnit() = weatherDao.deleteAllUnits()
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)

}