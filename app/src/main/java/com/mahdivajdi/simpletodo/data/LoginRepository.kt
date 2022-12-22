package com.mahdivajdi.simpletodo.data

import android.util.Log
import com.mahdivajdi.simpletodo.data.model.LoggedInUser
import com.mahdivajdi.simpletodo.data.model.LoginUser
import com.mahdivajdi.simpletodo.data.remote.LoginDataSource

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun login(user: LoginUser): NetworkResult<LoggedInUser> {
        // handle login
        val result = dataSource.login(user)

        if (result is NetworkResult.Success) {
            Log.d("LoginOp", "LoggedInUser data= ${result.data} ---  isLoggedIn= $isLoggedIn")
            setLoggedInUser(result.data)
            Log.d("LoginOp", "rep user= $user")
            Log.d("LoginOp", "isUserLoggedIn= $isLoggedIn")
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}