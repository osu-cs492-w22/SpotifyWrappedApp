package com.example.spotifywrapped.data


import java.io.Serializable

data class SpotifyItem(

    val id: String,
    val name: String,
    val images: List<SpotifyImage>?,
    val external_urls: SpotifyUrl

): Serializable

data class SpotifyImage(

    val url: String
) : Serializable


data class SpotifyUrl(

    val spotify: String

): Serializable