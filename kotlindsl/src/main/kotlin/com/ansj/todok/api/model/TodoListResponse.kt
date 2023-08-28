package com.ansj.todok.api.model

import com.ansj.todok.domain.Todo
import com.fasterxml.jackson.annotation.JsonIgnore


data class TodoListResponse(
    val items: List<TodoResponse>,
) {

    val size : Int
        @JsonIgnore // jackson json 이 자동으로 json으로 응답하기전에 ignore처리
        get() = items.size

    fun get(index: Int) = items[index]

    companion object {
        fun of(todoList: List<Todo>) =
            TodoListResponse(todoList.map(TodoResponse::of))
    }

}