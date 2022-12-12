package com.mahdivajdi.simpletodo.data.model

import com.squareup.moshi.Json

data class User(
    @Json(name = "last_name")
    val lastName: String = "",
    @Json(name = "pk")
    val pk: Int = 0,
    @Json(name = "first_name")
    val firstName: String = "",
    @Json(name = "email")
    val email: String = "",
    @Json(name = "username")
    val username: String = "",
)


data class RegisterUser(
    @Json(name= "userName")
    val userName: String,
    @Json(name= "email")
    val email: String,
    @Json(name= "password1")
    val password: String,
    @Json(name= "password2")
    val passwordRepeat: String,
)


data class LoginUser(
    @Json(name= "username")
    val userName: String,
    @Json(name= "password")
    val password: String,
)


data class LoggedInUser(
    @Json(name = "access_token")
    val accessToken: String = "",
    @Json(name = "refresh_token")
    val refreshToken: String = "",
    @Json(name = "user")
    val user: User,
)


data class AccessToken(
    @Json(name = "token")
    val token: String,
)


data class RefreshToken(
    @Json(name = "refresh")
    val token: String
)

