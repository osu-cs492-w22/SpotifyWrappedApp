package com.example.spotifywrapped.ui.login

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.spotifywrapped.R
import com.example.spotifywrapped.WrappedResultsActivity
import com.example.spotifywrapped.data.SessionManager
import com.example.spotifywrapped.databinding.ActivityLoginBinding

import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

object SpotifyConstants {
    val CLIENT_ID = "e52d26b096564c6299aea975dac46191"
    val AUTH_TOKEN_REQUEST_CODE = 2000
    val CALLBACK_URI = "com.example.spotifywrapped://callback"
}

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var sessionManager: SessionManager
//    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        val login = findViewById<Button>(R.id.login)
//        val loading = binding.loading

        sessionManager = SessionManager(this)

        login.setOnClickListener {

            Log.d("SpotifyLogin", "LOGIN BTN CLICKED")
            val request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN)
            AuthorizationClient.openLoginActivity(
                this,
                SpotifyConstants.AUTH_TOKEN_REQUEST_CODE,
                request
            )
//            LoginViewModel.connect()
        }

    }

    private fun getAuthenticationRequest(type: AuthorizationResponse.Type): AuthorizationRequest {
        return AuthorizationRequest.Builder(
            SpotifyConstants.CLIENT_ID,
            type,
            SpotifyConstants.CALLBACK_URI
        )
            .setShowDialog(false)
            .setScopes(arrayOf(
                "user-read-recently-played",
                "user-top-read",
            ))
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (SpotifyConstants.AUTH_TOKEN_REQUEST_CODE == requestCode) {
            val res = AuthorizationClient.getResponse(resultCode, data)
            val accessToken: String? = res.accessToken

            when (res.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    Log.d(
                        "SpotifyLogin",
                        "Access Token: $accessToken"
                    )

                    // add to shared prefs
//                    val editor = sharedPreferences.edit()

                    sessionManager.saveToken(accessToken!!)

//                    editor.putString(getString(R.string.token_val), accessToken)
//                    editor.apply()

//                    val savedToken = sharedPreferences.getString(getString(R.string.token_val), "token_val")

                    Log.d("SpotifyLogin", "savedToken: ${sessionManager.getToken()}")

                    val intent = Intent(this, WrappedResultsActivity::class.java)
                    startActivity(intent)

                }
                AuthorizationResponse.Type.ERROR -> Log.d(
                    "SpotifyLogin",
                    "RESPONSE(ERROR): ${res.error}"
                )

                else -> Log.d("SpotifyLogin", "Something else happened")
            }
        }
    }
}