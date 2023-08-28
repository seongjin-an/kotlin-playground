package com.ansj.todok.service

import com.ansj.todok.domain.Todo
import com.ansj.todok.domain.TodoRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
class TodoServiceTests {

    @MockkBean
    lateinit var repository: TodoRepository

    lateinit var service: TodoService

    val stub: Todo by lazy {
        Todo(
            id = 1,
            title = "테스트",
            description = "테스트 상세",
            done = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )
    }

    @BeforeEach
    fun setUp() {
        service = TodoService(repository)
    }


    @Test
    fun `한개의 TODO를 반환해야한다`() {
        // Given
        every { repository.findByIdOrNull(1L) } returns stub

        // When
        val actual = service.findById(1L)

        // Then
        assertThat(actual).isNotNull
        assertThat(actual).isEqualTo(stub)
    }

}
/*
잘 정리된 Transactional
https://ssdragon.tistory.com/116#%F-%-F%A-%--%--%EA%B-%B-%EB%A-%--%EB%-B%A-%EB%A-%B-%--%EC%A-%--%EC%--%B-%--%EB%A-%-C%EB%--%A-%EC%-D%--%--%ED%--%B-%EA%B-%B-%ED%--%--%EB%-A%--%--%EB%B-%A-%EB%B-%--%EC%-D%--%--%EB%AC%B-%EC%--%--%EC%-D%B-%--%EC%-E%--%EC%-D%--%EA%B-%-C%-F
https://resilient-923.tistory.com/415
 */