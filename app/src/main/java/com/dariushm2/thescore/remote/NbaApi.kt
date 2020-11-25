package com.dariushm2.thescore.remote

import android.content.Context
import com.dariushm2.thescore.remote.model.TeamResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface NbaApi {

    @GET("teams")
    suspend fun getTeams(): Response<List<TeamResponse>>


    companion object {

        private const val BASE_URL = "https://nba.com/"


        operator fun invoke(context: Context): NbaApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(MockClient(context))
                .build()


            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NbaApi::class.java)
        }
    }
}