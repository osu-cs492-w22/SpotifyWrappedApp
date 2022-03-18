package com.example.spotifywrapped.data

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
                val results = service.searchResults(type, 5, 0, "medium_term")
                Result.success(results)
            }catch(e: Exception){
                Result.failure(e)
            }
        }

}