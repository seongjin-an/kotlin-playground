package com.ansj.userservice.domain.service

import com.ansj.userservice.domain.entity.User
import com.ansj.userservice.domain.model.SignUpRequest
import com.ansj.userservice.domain.repository.UserRepository
import com.ansj.userservice.exception.UserExistException
import com.ansj.userservice.utils.BCryptUtils
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
) {
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
}