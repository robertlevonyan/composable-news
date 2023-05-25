package com.robertlevonyan.composable.newsapp.data.remote

import com.robertlevonyan.composable.newsapp.data.entity.ActionResult
import io.ktor.client.request.*
import javax.inject.Inject

class ApiService @Inject constructor(val httpClientWrapper: HttpClientWrapper) {
    suspend inline fun <reified T> get(
        url: String,
        parameters: Map<String, Any> = emptyMap(),
        headers: Map<String, Any> = emptyMap(),
    ): T = httpClientWrapper.getClient().get(url) {
        parameters.forEach { param -> parameter(param.key, param.value) }
        headers.forEach { head -> header(head.key, head.value) }
    } as T
}

suspend fun <R> makeApiCall(call: suspend () -> R) = try {
    ActionResult.Success(call())
} catch (e: Exception) {
    ActionResult.Error(e)
}
