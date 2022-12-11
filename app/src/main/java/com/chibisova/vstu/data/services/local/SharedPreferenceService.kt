package com.chibisova.vstu.data.services.local

import com.chibisova.vstu.domain.model.User

interface SharedPreferenceService {

    fun saveUser(user: User)

    fun readUser(): User

    fun containsUser(): Boolean

    fun deleteUser()

}