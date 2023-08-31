package com.ansj.domain.enums

enum class IssueType {
    BUG, TASK;


    companion object {
        // 정적 팩토리얼함수, 자바에서 흔하게 사용하는 방식
//        fun of (type: String) = valueOf(type.uppercase())
        operator fun invoke(type: String) = valueOf(type.uppercase())
    }
}

fun main() {
    //
//    val type = IssueType.of("BUG")
//    if (IssueType.BUG == type) {
//        println("YES")
//    }
    val type = IssueType("BUG")
}