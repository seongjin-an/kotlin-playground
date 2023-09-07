import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun sum(a: Int, b: Int) = a + b

fun main() = runBlocking<Unit> {
    // async 를 사용하면 비동기 처리에 대한 결과값을 받을 수 있다.
    // 또한 launch처럼 병렬로 실행할 수 있다.
    // async는 반환타입이 Deferred<T> 이다.
    val result1: Deferred<Int> = async {
        delay(100)
        sum(1, 3)
    }

    println("result1 : ${result1.await()}")

    val result2: Deferred<Int> = async {
        delay(100)
        sum(2, 5)
    }

    println("result2 : ${result2.await()}")
}