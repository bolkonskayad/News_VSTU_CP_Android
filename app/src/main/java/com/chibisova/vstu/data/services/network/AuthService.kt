package com.chibisova.vstu.data.services.network

import com.chibisova.vstu.data.dto.network.UserResponse
import com.chibisova.vstu.data.dto.network.LoginUserRequest
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Интирефейс сущности, который выполняет авторизацию/логаут
 */
interface AuthService {

    fun loginIn(userRequestData: LoginUserRequest?): Single<UserResponse>

    fun logout(): Completable

}