package com.example.spotifywrapped.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.Result

class SpotifyResultsRepository(

    private val service: SpotifyService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) {

    suspend fun loadSpotifyResults(
        type: String

    ): Result<SpotifyItems> =
        withContext(ioDispatcher){
            try{
                val results = service.searchResults(15, 0, "medium_term")
                Log.d("loaded Results", results.toString())
                Result.success(results)
            }catch(e: Exception){
                Log.d("error", e.toString())
                Result.failure(e)
            }
        }

}