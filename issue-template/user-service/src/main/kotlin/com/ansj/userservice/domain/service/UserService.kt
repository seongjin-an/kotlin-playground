package com.ansj.userservice.domain.service

import com.ansj.userservice.config.JWTProperties
import com.ansj.userservice.domain.entity.User
import com.ansj.userservice.domain.model.SignInRequest
import com.ansj.userservice.domain.model.SignInResponse
import com.ansj.userservice.domain.model.SignUpRequest
import com.ansj.userservice.domain.repository.UserRepository
import com.ansj.userservice.exception.InvalidJwtTokenException
import com.ansj.userservice.exception.PasswordNotMatchedException
import com.ansj.userservice.exception.UserExistException
import com.ansj.userservice.exception.UserNotFoundException
import com.ansj.userservice.utils.BCryptUtils
import com.ansj.userservice.utils.JWTClaim
import com.ansj.userservice.utils.JWTUtils
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserService (
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<User>,
) {

    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistException()
            }
            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username,
            )
            userRepository.save(user)
        }
    }

    suspend fun signIn(request: SignInRequest): SignInResponse {
        return with(userRepository.findByEmail(request.email) ?: throw UserNotFoundException()) {
            val verified = BCryptUtils.verify(request.password, password)
            if (!verified) {
                throw PasswordNotMatchedException()
            }
            val jwtClaim = JWTClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username
            )
            val token = JWTUtils.createToken(jwtClaim, jwtProperties)
            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)
            SignInResponse(
                email = email,
                username = username,
                token = token
            )
        }
    }

    suspend fun logout(token: String) {
        cacheManager.awaitEvict(token)
    }

    suspend fun getByToken(token: String): User {
        val cachedUser = cacheManager.awaitGetOrPut(key = token, ttl = CACHE_TTL) {
            // 캐시가 유효하지 않은 경우 동작.
            val decodedJWT = JWTUtils.decode(token, jwtProperties.secret, jwtProperties.issuer)

            val userId: Long = decodedJWT.claims["userId"]?.asLong() ?: throw InvalidJwtTokenException()
            get(userId)
        }
        return cachedUser
    }

    suspend fun get(userId: Long): User {
        return userRepository.findById(userId) ?: throw UserNotFoundException()
    }

    suspend fun edit(token: String, username: String, profileUrl: String?): User {
        val user = getByToken(token)
        val newUser = user.copy(username = username, profileUrl = profileUrl ?: user.profileUrl)
        return userRepository.save(newUser).also {
            cacheManager.awaitPut(key = token, value = it, ttl = CACHE_TTL)
        }
    }
}