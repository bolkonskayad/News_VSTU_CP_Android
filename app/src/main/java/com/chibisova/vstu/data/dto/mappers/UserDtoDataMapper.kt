package com.chibisova.vstu.data.dto.mappers

import com.chibisova.vstu.data.dto.network.UserResponse
import com.chibisova.vstu.domain.model.User

class UserDtoDataMapper {

    fun transform(userResponse: UserResponse): User {
        val userInfo = userResponse.userInfo
        return User(userInfo.id,
            userInfo.username,
            userInfo.firstName,
            userInfo.lastName,
            userInfo.userDescription)
    }

}