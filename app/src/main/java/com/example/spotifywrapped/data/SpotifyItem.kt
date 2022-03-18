package com.example.spotifywrapped.data


import java.io.Serializable

data class SpotifyItem(

    val id: String,
    val name: String,
    val images: List<SpotifyImage>


): Serializable

data class SpotifyImage(

    val url: String
)
