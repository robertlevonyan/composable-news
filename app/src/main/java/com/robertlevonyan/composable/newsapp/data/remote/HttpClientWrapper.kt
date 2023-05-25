package com.robertlevonyan.composable.newsapp.data.remote

//import io.ktor.client.features.json.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject

class HttpClientWrapper @Inject constructor() {
  fun getClient() = HttpClient(Android) {
    engine {
      connectTimeout = 100_000
      socketTimeout = 100_000
    }
    install(ContentNegotiation) {
      json(Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
      })
    }
  }
}
