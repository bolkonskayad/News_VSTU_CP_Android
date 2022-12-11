package com.chibisova.vstu.domain.repository

import com.chibisova.vstu.data.dto.network.LoginUserRequest
import com.chibisova.vstu.domain.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


interface UserRepository {

    fun getUser(userRequest: LoginUserRequest?): Single<User>

    fun getUser(): Single<User>

    fun containsUser(): Single<Boolean>

    fun deleteUser(): Completable

}