package com.dariushm2.thescore.remote

import android.content.Context
import com.dariushm2.thescore.remote.model.TeamResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NbaApi {

    @GET("teams")
    //@GET("nba-team-viewer")
    suspend fun getTeams(): Response<List<TeamResponse>>


    companion object {

        private const val BASE_URL = "https://nba.com/"
        //private const val BASE_URL = "https://github.com/scoremedia/"


        operator fun invoke(context: Context): NbaApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(MockClient(context))
                .build()

            val gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(NbaApi::class.java)
        }
    }
}