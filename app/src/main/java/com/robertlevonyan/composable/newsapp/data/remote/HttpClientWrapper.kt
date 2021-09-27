package com.robertlevonyan.composable.newsapp.data.remote

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import javax.inject.Inject

class HttpClientWrapper @Inject constructor() {
    fun getClient() = HttpClient(Android) {
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }
        install(JsonFeature) {
            serializer = GsonSerializer {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }
    }
}
