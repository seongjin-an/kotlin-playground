package com.ansj.springdatar2dbc

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@SpringBootApplication
class SpringDataR2dbcApplication {

    @Bean
    fun init(connectionFactory: ConnectionFactory) = ConnectionFactoryInitializer().apply {
        setConnectionFactory(connectionFactory)
        setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("scripts/schema.sql")))
    }

}

fun main(args: Array<String>) {
    runApplication<SpringDataR2dbcApplication>(*args)
}
