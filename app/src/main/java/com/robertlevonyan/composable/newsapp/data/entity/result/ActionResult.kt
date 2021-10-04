package com.robertlevonyan.composable.newsapp.data.entity.result

sealed class ActionResult<out R>

data class Success<out R>(val data: R) : ActionResult<R>()
object Error : ActionResult<Nothing>()
