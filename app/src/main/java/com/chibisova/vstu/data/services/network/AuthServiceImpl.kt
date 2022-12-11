package com.chibisova.vstu.data.services.network

import com.chibisova.vstu.data.api.AuthApi
import com.chibisova.vstu.data.dto.network.LoginUserRequest
import com.chibisova.vstu.data.dto.network.UserResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val api: AuthApi
): AuthService {

    override fun loginIn(userRequestData: LoginUserRequest?): Single<UserResponse> {
        return api.loginIn(userRequestData)
    }

    //TODO прийти к единому вызову Completable or Maybe<>
    override fun logout(): Completable {
        return api.logout().ignoreElement()
    }

}