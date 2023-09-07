import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

/*
flow(플로우)
플로우는 코루틴에서 리액티브 프로그래밍 스타일로 작성할 수 있도록 만들어진 API 이다.
 */

fun main() = runBlocking<Unit> {
    val flow = simple()
//    println(flow) // 기대와 달리 레퍼런스만 나옴 이유는 terminal operator 를 호출하지 않았기 때문이다.
    flow.collect { value -> println(value) } // terminal operator, 이것을 호출해야 flow가 비로소 동작하는 것.

}

fun simple(): Flow<Int> = flow {
    // flow 빌더를 사용해야 함.
    println("Flow started")

    for (i in 1..3) {
        delay(100)
        emit(i) // 이것을 통해 데이터를 통제한다.
    }
}