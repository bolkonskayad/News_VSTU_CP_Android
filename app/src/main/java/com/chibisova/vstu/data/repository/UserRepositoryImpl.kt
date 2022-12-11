package com.chibisova.vstu.data.repository

import com.chibisova.vstu.data.dto.mappers.UserDtoDataMapper
import com.chibisova.vstu.data.dto.network.LoginUserRequest
import com.chibisova.vstu.data.services.local.SharedPreferenceService
import com.chibisova.vstu.data.services.network.AuthService
import com.chibisova.vstu.domain.model.User
import com.chibisova.vstu.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val sharedPreferenceService: SharedPreferenceService,
    private val authService: AuthService,
    private val mapper: UserDtoDataMapper
) : UserRepository {

    override fun getUser(userRequest: LoginUserRequest?): Single<User> {
        return authService.loginIn(userRequest)
            .map { mapper.transform(it) }
            .doOnSuccess { sharedPreferenceService.saveUser(it) }
            .subscribeOn(Schedulers.io())
    }

    override fun getUser(): Single<User> {
        return Single.just(sharedPreferenceService.readUser())
            .subscribeOn(Schedulers.io())
    }

    override fun containsUser(): Single<Boolean> {
        return Single.just(sharedPreferenceService.containsUser())
            .subscribeOn(Schedulers.io())
    }

    override fun deleteUser(): Completable {
        return authService.logout()
            .doOnComplete { sharedPreferenceService.deleteUser() }
            .subscribeOn(Schedulers.io())
    }


}