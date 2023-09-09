package com.ansj.userservice.exception

sealed class ServerException(
    val code: Int,
    override val message: String,
): RuntimeException()

data class UserExistException(
    override val message: String = "이미 존재하는 유저입니다."
) : ServerException(409,message)