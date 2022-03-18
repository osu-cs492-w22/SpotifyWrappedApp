package com.example.spotifywrapped.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.spotifywrapped.data.SpotifyItems
import com.example.spotifywrapped.data.SpotifyResultsRepository
import com.example.spotifywrapped.data.SpotifyService
import kotlinx.coroutines.launch

class SpotifyViewModel(application: Application): AndroidViewModel(application) {

    private val resultsRepo = SpotifyResultsRepository(SpotifyService.create(application))

    private val _spotifyResults = MutableLiveData<SpotifyItems?>(null)
    val spotifyResults: LiveData<SpotifyItems?> = _spotifyResults

    fun loadResults(
        type: String,

    ){
        viewModelScope.launch {
            val result = resultsRepo.loadSpotifyResults(type)
            _spotifyResults.value = result.getOrNull()

        }

    }
}