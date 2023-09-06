package com.ansj.springreactiveweb.book

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.atomic.AtomicInteger

data class Book(val id: Int, val name: String, val price: Int)

@Service
class BookService {

    private final val nextId = AtomicInteger(0)

    val books = mutableListOf(
        Book(id = nextId.incrementAndGet(), name = "코틀린 인 액션", price = 30000),
        Book(id = nextId.incrementAndGet(), name = "HTTP 완벽 가이드", price = 40000)
    )

    fun getAll(): Flux<Book> {
        return Flux.fromIterable(books)
    }

    fun get(id: Int): Mono<Book> {
        return Mono.justOrEmpty(books.find { it.id == id })
    }

    fun add(request: Map<String, Any>): Mono<Book> {
        return Mono.just(request)
            .map { req ->
                val book = Book(
                    id = nextId.incrementAndGet(),
                    name = req["name"].toString(),
                    price = req["price"] as Int
                )
                books.add(book)
                book
            }
    }

    fun delete(id: Int): Mono<Void> {
        return Mono.justOrEmpty(books.find { it.id == id })
            .map { books.remove(it) }
            .then()
    }
}