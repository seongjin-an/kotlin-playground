import kotlinx.coroutines.runBlocking

/*
runblocking
코루틴을 생성하는 코루틴 빌더이다.
이것으로 감싸면 코루틴 내부 로직 수행이 끝날 때까지 해당 쓰레드는 블로킹된다.

runBlocking 은 비동기로 동작하지 않고 동기적으로 작동함.(잘 사용하지 않음)
특정 코드, 라이브러리에서 코루틴을 지원하지 않는 경우가 있음
테스트를 작성한다던가, 스프링배치 같은 프레임워크에서 코루틴을 지원하지 않는 경우에는
블로킹으로 동작하여 일반적인 스프링 MVC 스타일로 코드를 작성해야 함. 그때는 runBlocking
 */
fun main() {
    runBlocking {
        println("Hello")
        println(Thread.currentThread().name) // VM options에 -Dkotlinx.coroutines.debug 를 명시해야 아래처럼 코루틴 쓰레드명칭이 확인됨.
    }
    println("World")
    println(Thread.currentThread().name)
}