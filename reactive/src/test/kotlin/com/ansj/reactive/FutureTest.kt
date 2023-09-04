package com.ansj.reactive

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun sum(a: Int, b: Int) = a + b

fun main() {
    val pool = Executors.newSingleThreadExecutor()
    val future = pool.submit(Callable {
        sum(100, 200)
    })

    println("계산 시작")
    val futureResult = future.get(1000L, TimeUnit.MILLISECONDS) // 비동기 작업의 결과를 기다린다. 비동기 블로킹.
    println(futureResult)
    println("계산 종료")
}