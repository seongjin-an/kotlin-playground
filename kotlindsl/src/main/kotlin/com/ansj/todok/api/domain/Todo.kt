package com.ansj.todok.api.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "todos")
class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(name = "title")
    var title: String,

    @Lob
    @Column(name = "description")
    var description: String,

    @Column(name = "done")
    var done: Boolean,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime,
) {


    fun update(title: String, description: String, done: Boolean?) {
        this.title = title
        this.description = description
        this.done = done != null && done
        updatedAt = LocalDateTime.now()
    }
}