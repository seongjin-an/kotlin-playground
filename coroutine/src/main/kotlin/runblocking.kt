import kotlinx.coroutines.runBlocking

/*
runblocking
코루틴을 생성하는 코루틴 빌더이다.
이것으로 감싸면 코루틴 내부 로직 수행이 끝날 때까지 해당 쓰레드는 블로킹된다.
 */
fun main() {
    runBlocking {
        println("Hello")
        println(Thread.currentThread().name)
    }
    println("World")
    println(Thread.currentThread().name)
}