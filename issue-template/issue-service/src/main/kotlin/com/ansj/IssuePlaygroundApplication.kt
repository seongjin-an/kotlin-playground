package com.ansj

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IssuePlaygroundApplication

fun main(args: Array<String>){
    runApplication<IssuePlaygroundApplication>(*args)
}