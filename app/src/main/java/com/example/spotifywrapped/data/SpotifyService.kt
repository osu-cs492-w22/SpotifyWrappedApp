package com.example.spotifywrapped.data

import androidx.preference.PreferenceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.io.IOException
import kotlin.coroutines.coroutineContext


interface SpotifyService {

    @Headers(
        "Accept: application/json",
        "Content-type: application/json",
        "Authorization: Bearer BQC41rgG3g4cQ4fX89MnTJ2jqBl06TBIjy8UPyWQ2uiSuOUPsYP8h8noBlM2YzjHBA_L1NLC-cwMqy23geg_-laOokRC_I3CJkQK3iDcowMiMRXMKNEV3C4cIqBn-ZdqtYJDFF-sVx_0U9HxzrIfK1MlBlzU",
        "Host: api.spotify.com"
    )
    @GET("https://api.spotify.com/v1/me/top/tracks")
    suspend fun searchResults(



//        @Path ("type")type: String,
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

//            val token = PreferenceManager.getDefaultSharedPreferences()
//
////            httpClient.requestBuilder.addHeader("Authorization: ,", "Bearer $token");
//
//            httpClient.networkInterceptors().add(object : Interceptor {
//                @Throws(IOException::class)
//                override fun intercept(chain: Interceptor.Chain): Response {
//                    val requestBuilder: Request.Builder = chain.request().newBuilder()
//                    requestBuilder.header(
//                        "Content-Type", "application/json",
//
//                    )
//                    return chain.proceed(requestBuilder.build())
//                }
//            })

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(httpClient.build())
                .build()

            return retrofit.create(SpotifyService::class.java)
        }

    }

}