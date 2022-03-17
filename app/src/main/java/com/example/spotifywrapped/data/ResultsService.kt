package com.example.spotifywrapped.data

import retrofit2.http.GET

interface ResultsService {

    @GET("https://api.spotify.com/v1/me")
    fun getTopItems(
        type: String,
        limit: Int,
        offset: Int,
        range: String,
        apiKey: String
    ) : Unit
}