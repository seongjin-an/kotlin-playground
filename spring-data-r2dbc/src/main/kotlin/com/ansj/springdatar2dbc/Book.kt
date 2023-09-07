package com.ansj.springdatar2dbc

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

/*
jdbc와는 다르게 스프링 데이터 R2DBC는 더티체킹, LazyLoading을 지원하지 않음.
그러니 val, data 키워드 사용해도 됨.
 */
@Table
data class Book(
    @Id
    val id: Long? = null,

    @Column
    val name: String,

    @Column
    val price: Int,
) {
}