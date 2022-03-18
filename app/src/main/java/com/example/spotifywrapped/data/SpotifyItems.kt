package com.example.spotifywrapped.data

import java.io.Serializable


data class SpotifyItems(

    val href: String,
    val limit: Int,
    val items: List<SpotifyItem>

):Serializable
