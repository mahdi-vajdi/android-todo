package com.mahdivajdi.simpletodo.data

import com.mahdivajdi.simpletodo.data.local.UserPreferences
import com.mahdivajdi.simpletodo.data.remote.model.LoginApiResponseModel
import com.mahdivajdi.simpletodo.data.remote.model.LoginUserRequestModel
import com.mahdivajdi.simpletodo.data.remote.model.RegisterUserRequestModel
import com.mahdivajdi.simpletodo.data.remote.AuthDataSource
import com.mahdivajdi.simpletodo.data.remote.NetworkResult

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class AuthRepository(
    private val dataSource: AuthDataSource,
    private val preferences: UserPreferences
    ) {

    suspend fun login(user: LoginUserRequestModel): NetworkResult<LoginApiResponseModel> = dataSource.login(user)

    suspend fun register(user: RegisterUserRequestModel): NetworkResult<LoginApiResponseModel> =
        dataSource.register(user)

    suspend fun saveAuthTokens(refreshToken: String, accessToken: String) {
        preferences.saveRefreshToken(refreshToken)
        preferences.saveAccessToken(accessToken)
    }

}