package com.example.todoibrahimaberete_brayanekutlar.network

import com.example.todoibrahimaberete_brayanekutlar.tasklist.Task
import retrofit2.Response
import retrofit2.http.GET

interface TaskWebService {
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>
}