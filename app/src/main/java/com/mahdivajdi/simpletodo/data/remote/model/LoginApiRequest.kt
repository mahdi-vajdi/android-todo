package com.mahdivajdi.simpletodo.data.remote.model

import com.squareup.moshi.Json

data class RegisterUserRequestModel(
    @Json(name= "username")
    val userName: String,
    /*@Json(name= "email")
    val email: String = "",*/
    @Json(name= "password1")
    val password: String,
    @Json(name= "password2")
    val passwordRepeat: String,
)


data class LoginUserRequestModel(
    @Json(name= "username")
    val userName: String,
    @Json(name= "password")
    val password: String,
)


data class AccessTokenRequestModel(
    @Json(name = "token")
    val token: String,
)


data class RefreshTokenRequestModel(
    @Json(name = "refresh")
    val token: String
)
