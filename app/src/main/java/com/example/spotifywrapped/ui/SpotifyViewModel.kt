package com.example.spotifywrapped.ui

import androidx.lifecycle.*
import com.example.spotifywrapped.data.SpotifyItems
import com.example.spotifywrapped.data.SpotifyResultsRepository
import com.example.spotifywrapped.data.SpotifyService
import kotlinx.coroutines.launch

class SpotifyViewModel: ViewModel() {

    private val weather = SpotifyResultsRepository(SpotifyService.create())

    private val _spotifyResults = MutableLiveData<SpotifyItems?>(null)
    val spotifyResults: LiveData<SpotifyItems?> = _spotifyResults

    fun loadResults(
        type: String,

    ){
        viewModelScope.launch {
            val result = weather.loadSpotifyResults(type)
            _spotifyResults.value = result.getOrNull()

        }

    }
}