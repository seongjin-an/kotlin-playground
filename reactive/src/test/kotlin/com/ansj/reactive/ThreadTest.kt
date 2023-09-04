package com.ansj.reactive

fun main() {
    for (i in 0..5) {
        val thread = Thread(Runnable {
            println("current-thread-name : ${Thread.currentThread().name}")
        })
        thread.start()
    }
    // main thread 는 jvm 언어에서 최초의 프로세스가 시작될 때, 가장 기본이 되는 쓰레드를 말함.
    println("current-thread-name : ${Thread.currentThread().name}")
}