package com.mahdivajdi.simpletodo.data

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response


sealed class Result<out T : Any> {

    data class Success<out T: Any>(val data: T) : Result<T>()

    data class Error<out T: Any>(val code: Int, val message: String?) : Result<T>()

    data class Exception<out T: Any>(val e: Throwable) : Result<T>()

}


suspend fun <T : Any> handleNetworkResult(execute: suspend () -> Response<T>): Result<T> {
    return try {
        val response = execute()
        Log.d("LoginResponse", "$response")
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            Result.Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        Result.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        Result.Exception(e)
    }
}

