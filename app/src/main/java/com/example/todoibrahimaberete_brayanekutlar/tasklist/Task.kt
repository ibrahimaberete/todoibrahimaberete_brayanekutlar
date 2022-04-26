package com.example.todoibrahimaberete_brayanekutlar.tasklist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    @SerialName("id")
    val id:String,
    @SerialName("title")
    val title:String,
    @SerialName("description")
    val description:String = "valeur par defaut"):java.io.Serializable
