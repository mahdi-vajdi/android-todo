package com.mahdivajdi.simpletodo.data.remote.model

import com.squareup.moshi.Json


data class LoginApiResponseModel(
    @Json(name = "access_token")
    val accessToken: String = "",
    @Json(name = "refresh_token")
    val refreshToken: String = "",
    @Json(name = "user")
    val user: UserRemoteModel,
)


data class UserRemoteModel(
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