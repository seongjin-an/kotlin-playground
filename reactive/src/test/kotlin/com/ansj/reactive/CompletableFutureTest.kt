package com.ansj.reactive

import java.util.concurrent.CompletableFuture

fun main() {
    val completableFuture = CompletableFuture.supplyAsync {
        Thread.sleep(2000)
        sum(100, 200)
    }

    println("계산 시작")
    completableFuture.thenApplyAsync(::println) // 논블로킹으로 동작

//    val result = completableFuture.get() // 블로킹으로 동작
//    println(result)

    while (!completableFuture.isDone) { // 블로킹이 될 경우 이 부분은 실행 안함.
        Thread.sleep(500)
        println("계산 결과를 집계 중입니다.")
    }
    println("계산 종료")
}
/*
계산 시작
계산 결과를 집계 중입니다.
계산 결과를 집계 중입니다.
계산 결과를 집계 중입니다.
300
계산 결과를 집계 중입니다.
계산 종료
 */