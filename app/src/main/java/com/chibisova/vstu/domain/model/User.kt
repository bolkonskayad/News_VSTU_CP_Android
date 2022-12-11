package com.chibisova.vstu.domain.model


data class User(
    val id: Int,
    var username: String,
    var firstName: String,
    var lastName: String,
    var descriptionProfile: String
) {
    val isEmpty: Boolean
        get() = username == "" && firstName == "" && lastName == "" && descriptionProfile == ""
}