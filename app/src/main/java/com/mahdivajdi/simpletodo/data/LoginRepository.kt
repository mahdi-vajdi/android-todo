package com.mahdivajdi.simpletodo.data

import com.mahdivajdi.simpletodo.data.remote.model.LoginApiResponseModel
import com.mahdivajdi.simpletodo.data.remote.model.LoginUserRequestModel
import com.mahdivajdi.simpletodo.data.remote.model.RegisterUserRequestModel
import com.mahdivajdi.simpletodo.data.remote.LoginDataSource

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(
    private val dataSource: LoginDataSource,
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