package com.example.spotifywrapped.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel

import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

class LoginViewModel() : ViewModel() {

    val CLIENT_ID = "e52d26b096564c6299aea975dac46191"
    val CLIENT_SECRET = "ecb6cb7cc5c34b5bb5227f143a989815"
    val REDIRECT_URI = "https://localhost/callback/"

    private var spotifyAppRemote: SpotifyAppRemote? = null
    private var connectionParams: ConnectionParams = ConnectionParams.Builder(CLIENT_ID)
        .setRedirectUri(REDIRECT_URI)
        .showAuthView(true)
        .build()

    fun connect(context: Context, handler: (connected: Boolean) -> Unit) {
        if (spotifyAppRemote?.isConnected == true) {
            handler(true)
            return
        }

        val connectionListener = object : Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote?) {
                this@LoginViewModel.spotifyAppRemote = spotifyAppRemote
                handler(true)
            }

            override fun onFailure(error: Throwable?) {
                Log.e("SpotifyLogin", error?.message, error)
            }
        }
        SpotifyAppRemote.connect(context, connectionParams, connectionListener)
    }
}