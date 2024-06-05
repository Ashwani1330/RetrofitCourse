package com.example.retrofitcourse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import kotlin.coroutines.CoroutineContext

interface TodoApi {

    @GET("/todos")
    suspend fun getTodos(): Response<List<Todo>>

}