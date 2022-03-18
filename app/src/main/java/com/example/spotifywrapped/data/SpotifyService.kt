package com.example.spotifywrapped.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyService {

    @GET("https://api.spotify.com/v1/me/top/")
    suspend fun searchResults(

        @Path ("type")type: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query ("time_range")range: String,

        ):SpotifyItems

    companion object{
        private const val BASE_URL = "https://api.spotify.com"
        fun create():SpotifyService{

            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(httpClient.build())
                .build()

            return retrofit.create(SpotifyService::class.java)
        }

    }

}