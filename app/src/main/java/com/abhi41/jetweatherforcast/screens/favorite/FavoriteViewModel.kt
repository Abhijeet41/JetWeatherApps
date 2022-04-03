package com.abhi41.jetweatherforcast.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.jetweatherforcast.model.DbModel.Favorite
import com.abhi41.jetweatherforcast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "FavoriteViewModel"

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow() //Represents this mutable state flow as a read-only state flow.

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged() //repetitions of the same value are filtered out.
                .collect { listOfFavs ->
                    if (listOfFavs.isNullOrEmpty()) {
                        Log.d(TAG, ": Empty favs")
                    }else{
                        _favList.value = listOfFavs
                        Log.d(TAG, "fav_orig: ${favList.value}")
                    }
                }
        }


    }

    fun insertfavorite(favorite: Favorite) : Job {
        return viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
    }

    //just like insertFavourite we can also write in this way
    fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.updateFavorite(favorite)
    }

    //delete
    fun deleteFavourite(favorite: Favorite) = viewModelScope.launch {
        repository.deleteFavorite(favorite)
    }




}