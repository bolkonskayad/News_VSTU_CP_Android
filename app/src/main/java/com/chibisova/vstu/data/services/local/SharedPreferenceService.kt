package com.chibisova.vstu.data.services.local

import com.chibisova.vstu.domain.model.User

/**
 * Интерфейс для управления класса, который управляет SharedPref(внутренеее хранилище)
 */
interface SharedPreferenceService {

    fun saveUser(user: User)

    fun readUser(): User

    fun containsUser(): Boolean

    fun deleteUser()

}