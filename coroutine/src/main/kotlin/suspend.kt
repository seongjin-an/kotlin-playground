import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//fun main() {
//    doSomething() // error, suspend가 아닌 함수는 suspend 함수를 호출할 때제약이 있음.
//}
//fun main() = runBlocking {
//    doSomething()
//}
suspend fun main() {
    doSomething()
}

/*
함수 앞에 suspend 라는 키워드를 사용하면, 이 함수는 일시중단이 가능하고 재개가 가능한 함수를 말한다.
suspend 함수는 일반 함수를 마음껏 호출할 수 있지만, 일반함수에서는 suspend함수를 일반적으로는 바로 호출할 수가 없다.
 */
//suspend fun doSomething() {
//    printHello() // suspend 함수는 일반함수를 마음껏 부를 수 있음!
//}
/*
또한 launch, async 같은 코루틴을 사용하려면 코루틴 스코프(coroutineScope) 내에서 작성해야 함.
그리고 앞에서 살펴본 runBlocking과 다른 점으로는 쓰레드가 차단되지 않음(블로킹 되지 않음)
 */
suspend fun doSomething() = coroutineScope {
    launch {
        delay(200)
        println("world!")
    }
    launch {
        printHello()
    }
}

fun printHello() = println("hello")