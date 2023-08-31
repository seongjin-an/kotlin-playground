package com.ansj

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class IssuePlaygroundApplication

fun main(args: Array<String>){
    runApplication<IssuePlaygroundApplication>(*args)
}