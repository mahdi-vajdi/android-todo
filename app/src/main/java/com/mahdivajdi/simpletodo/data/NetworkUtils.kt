package com.mahdivajdi.simpletodo.data

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response


sealed class NetworkResult<out T : Any> {

    data class Success<out T: Any>(val data: T) : NetworkResult<T>()

    data class Error<out T: Any>(val code: Int, val message: String?) : NetworkResult<T>()

    data class Exception<out T: Any>(val e: Throwable) : NetworkResult<T>()

}


suspend fun <T : Any> handleNetworkResult(execute: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = execute()
        Log.d("LoginResponse", "$response")
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        NetworkResult.Exception(e)
    }
}

