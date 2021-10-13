package com.robertlevonyan.composable.newsapp.data.entity

sealed class ActionResult<out T> {
    data class Success<T>(val data: T) : ActionResult<T>()
    data class Error(val error: Throwable) : ActionResult<Nothing>()
}
