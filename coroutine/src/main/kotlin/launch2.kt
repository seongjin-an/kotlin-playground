import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
/*
launch는 Job이라는 특별한 리턴이 있다.
이것을 사용하면 launch 는 코루틴 상태를 확인할 수도 있고, 취소할 수도 있다.
Job이라는 리턴값은 실행의 흐름을 제어할 수는 있지만, 비동기 처리에 대한 결과를 가져온건 아님.
병렬로 여러 개를 작성할 수 있음.
 */
fun main() = runBlocking<Unit> {
    val job1: Job = launch {
        val elapsedTime = measureTimeMillis {
            delay(150)
        }
        println("async task-1 $elapsedTime ms")
    }
    job1.cancel()

    // 또한 launch는 LAZY 설정을 통해 동작을 뒤로 미룰 수도 있다.
    val job2: Job = launch(start = CoroutineStart.LAZY) {
        val elapsedTime = measureTimeMillis {
            delay(100)
        }
        println("async task-2 $elapsedTime ms")
    }

    println("start task-2")
    job2.start() // 이것을 호출하지 않으면 job2는 실행되지 않음. 명시적으로 start()를 호출해줘야 job2가 실행된다.

}