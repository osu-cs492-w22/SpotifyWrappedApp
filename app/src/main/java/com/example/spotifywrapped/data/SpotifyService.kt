package com.example.spotifywrapped.data

import android.content.Context
import com.example.spotifywrapped.data.utils.AuthInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


interface SpotifyService {

    @Headers(
        "Accept: application/json",
        "Content-type: application/json",
//        "Authorization: Bearer BQD7WCJ9mtzP9ScHYgnM-ro999Dgo9jC27MtwcJzDqq9EEAmjS8w1tCObGFKRhjtLIIL63fX3c2yXGnv4wz6iy_vGNW4gH7fAv_m-6SuU7IEP4G9khLoyroy738I_9U76EciUJy8xk17r5SOf6M0s_I2bStV",
        "Host: api.spotify.com"
    )
//    @Header("Authorization")
    @GET("https://api.spotify.com/v1/me/top/{type}")
    suspend fun searchResults(

        @Path("type")type: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query ("time_range")range: String,

        ):SpotifyItems

    companion object{
        private const val BASE_URL = "https://api.spotify.com"
        fun create(context: Context):SpotifyService{

            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor(AuthInterceptor(context)) // adds the auth token to the 'Authorization' header

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(httpClient.build())
                .build()

            return retrofit.create(SpotifyService::class.java)
        }

    }

}