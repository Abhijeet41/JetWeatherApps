package com.abhi41.jetweatherforcast.data

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.abhi41.jetweatherforcast.model.DbModel.Favorite
import com.abhi41.jetweatherforcast.model.DbModel.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("Select *  from fav_tbl")
    fun getFevorites(): Flow<List<Favorite>>

    @Query("Select * from fav_tbl where city = :city")
    suspend fun getFavById(city: String): Favorite

    @Insert(onConflict = REPLACE)
    suspend fun insertFavourite(favorite: Favorite)

    @Update(onConflict = REPLACE)
    suspend fun updateFavourite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavourites()

    @Delete
    suspend fun deleteFavorites(favorite: Favorite)

    //Unit Table
    @Query("SELECT * from setting_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertUnit(unit: Unit)

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE from setting_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)

}