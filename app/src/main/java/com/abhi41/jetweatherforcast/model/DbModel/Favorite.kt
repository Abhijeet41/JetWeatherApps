package com.abhi41.jetweatherforcast.model.DbModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "fav_tbl")
data class Favorite(

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city:String,

    @ColumnInfo(name = "country")
    val country: String
)
