import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    // launch는 결과를 반환하는 비동기 논블로킹 코루틴.
    launch {
        delay(5000)
        // 코루틴에서 제공함. 쓰레드를 차단하지 않고 하나의 Job을 일시중지함., Thread.sleep(500)은 쓰레드를 블로킹하지만
        println("World!")
    }
    println("Hello")
}