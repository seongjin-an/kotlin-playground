package com.ansj.todok.api.model

import com.ansj.todok.domain.Todo
import java.time.LocalDateTime

data class TodoResponse(
    val id : Long,
    val title : String,
    val description : String,
    val done : Boolean,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime?
) {

    companion object {
        fun of (todo : Todo?) : TodoResponse{
            checkNotNull(todo) { "Todo is null" }

            val id = todo.id
            checkNotNull(id) { "Todo Id is null" }

            return TodoResponse(
                id = id,
                title = todo.title,
                description = todo.description,
                done = todo.done,
                createdAt = todo.createdAt,
                updatedAt = todo.updatedAt
            )
        }
    }

}