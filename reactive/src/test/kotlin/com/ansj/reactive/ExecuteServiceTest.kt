package com.ansj.reactive

import java.util.concurrent.Executors

fun main() {
    val pool = Executors.newFixedThreadPool(5)
    try {
        for (i in 0..5) {
            pool.execute {
                println("current-thread-name : ${Thread.currentThread().name}")
            }
        }
    } finally {
        pool.shutdown()
    }
    println("current-thread-name : ${Thread.currentThread().name}")
}