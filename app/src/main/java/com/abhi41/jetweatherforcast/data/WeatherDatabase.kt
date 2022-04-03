package com.abhi41.jetweatherforcast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhi41.jetweatherforcast.model.DbModel.Favorite
import com.abhi41.jetweatherforcast.model.DbModel.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
//we set to version 2 for recompile without crash because we added new table (Unit.class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}