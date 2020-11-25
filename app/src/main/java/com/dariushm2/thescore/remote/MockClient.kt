package com.dariushm2.thescore.remote

import android.content.Context
import android.util.Log
import com.dariushm2.thescore.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class MockClient(
    private val context: Context
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val url: HttpUrl = chain.request().url
        when (url.toString()) {
            "https://nba.com/teams" -> {

                val response = withContext(Dispatchers.IO) { readJsonFileFromAssets() }
                Response.Builder()
                    .code(200)
                    .message(response)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(
                        response.toByteArray().toResponseBody("application/json".toMediaTypeOrNull())
                    )
                    .addHeader("content-type", "application/json")
                    .build()


            }
            else -> {
                Response.Builder()
                    .code(404)
                    .message("Page not found!")
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body("Page not found!".toResponseBody("application/json".toMediaTypeOrNull()))
                    .addHeader("content-type", "application/json")
                    .build()
            }
        }
    }

    private fun readJsonFileFromAssets(): String {
        val bufferedReader =
            BufferedReader(InputStreamReader(context.assets.open("teams.min.json")))
        return bufferedReader.readLine()
    }
}