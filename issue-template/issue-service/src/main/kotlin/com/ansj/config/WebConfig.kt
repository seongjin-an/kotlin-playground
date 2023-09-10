package com.ansj.config

import com.ansj.exception.UnauthorizedException
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver
) : WebMvcConfigurationSupport() {

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.apply {
            add(authUserHandlerArgumentResolver)
            // 스프링이 handlerMethodArgumentResolver 에 대해서 이 리스트를 반복을 돌며,
            // supportsParameter 가 부합하면 resolveArgument를 통해 컨트롤러 인자로 넘겨줌.
        }
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        // * : 포인터가 아님!!, kotlin spread operator
        registry.addResourceHandler("/**")
            .addResourceLocations(*arrayOf(
                "classpath:/META-INF/resource/",
                "classpath:/resources/",
                "classpath:/static/",
                "classpath:/public/",
            ))
    }
}

@Component
class AuthUserHandlerArgumentResolver(
    @Value("\${auth.url}") val authUrl: String,
) : HandlerMethodArgumentResolver {
    // 컨트롤러 인자로 들어오는 특정 객체, 애노테이션에 대해서 supportsParameter 조건에 부합하면, resolveArgument 를 통해서 해당
    // 애노테이션 혹은 객체에 대해서 값을 세팅해주거나 저장해주도록 함.
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return AuthUser::class.java.isAssignableFrom(parameter.parameterType) // 리플렉션 기능
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {

        val authHeader: String = webRequest.getHeader("Authorization") ?: throw UnauthorizedException()

//        return AuthUser(
//            userId = 1,
//            username = "테스트",
//        )
        return runBlocking {
            WebClient.create()
                .get()
                .uri(authUrl)
                .header("Authorization",authHeader)
                .retrieve()
                .awaitBody<AuthUser>()
        }
    }
}

data class AuthUser(
    @JsonProperty("id")
    val userId : Long,
    val username : String,
    val email: String,
    val profileUrl : String? = null,
)