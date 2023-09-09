package com.ansj.userservice.utils

import com.ansj.userservice.config.JWTProperties
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class JWTUtilsTest {
    private val logger = KotlinLogging.logger {}

    val jwtClaim = JWTClaim(
        userId = 1,
        email = "dev@gmail.com",
        profileUrl = "profile.jpg",
        username = "개발자"
    )
    val properties = JWTProperties(
        issuer = "jara",
        subject = "auth",
        expiresTime = 3600,
        secret = "my-secret"
    )

    val token = JWTUtils.createToken(jwtClaim, properties)

    @Test
    fun createTokenTest() {
        Assertions.assertThat(token).isNotNull
        logger.info("token: $token")
    }

    @Test
    fun decodeTest() {
        val decode = JWTUtils.decode(token, secret = properties.secret, issuer = properties.issuer)
        with (decode) {
            logger.info("claims: $claims")
            val userId = claims["userId"]!!.asLong()
            Assertions.assertThat(userId).isEqualTo(jwtClaim.userId)

            val email = claims["email"]!!.asString()
            Assertions.assertThat(email).isEqualTo(jwtClaim.email)

            val profileUrl = claims["profileUrl"]!!.asString()
            Assertions.assertThat(profileUrl).isEqualTo(jwtClaim.profileUrl)

            val username = claims["username"]!!.asString()
            Assertions.assertThat(username).isEqualTo(jwtClaim.username)
        }
    }
}