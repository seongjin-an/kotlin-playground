package com.ansj.exception

import com.auth0.jwt.exceptions.TokenExpiredException
import mu.KotlinLogging
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = KotlinLogging.logger {}

    @ExceptionHandler(ServerException::class)
    fun handleServerException(ex: ServerException) : ErrorResponse{
        log.error { ex.message }

        return ErrorResponse(code = ex.code, message = ex.message)
    }

    @ExceptionHandler(TokenExpiredException::class)
    fun handleTokenExpiredException(ex: TokenExpiredException) : ErrorResponse{
        log.error { ex.message }

        return ErrorResponse(code = 401, message = "Token Expired Error")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception) : ErrorResponse{
        log.error { ex.message }
        // exception message를 그대로 내려주면, 스택트레이스 메시지가 그대로 내려감.
        // 그럴경우, 어떤 언어, 어떤 프레임워크로 개발된건지, 어떤 DB 필드를 사용하는지, 정보가 전부 노출됨.
        // 그러니 그런 내용은 로거로 남기고, 응답은 다음과 같이 해야 함.
        return ErrorResponse(code = 500, message = "Internal Server Error")
    }

}