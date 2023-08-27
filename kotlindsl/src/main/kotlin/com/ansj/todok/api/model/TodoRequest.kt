package com.ansj.todok.api.model

data class TodoRequest (
    val title : String,
    val description : String,
    val done : Boolean = false,
)