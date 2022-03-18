package com.example.spotifywrapped.data

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {

    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("Spotify", "INTERCEPTOR CALLED")
        Log.d("Spotify", "${sessionManager.getToken()}")

        val requestBuilder = chain.request().newBuilder()

        sessionManager.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }

}