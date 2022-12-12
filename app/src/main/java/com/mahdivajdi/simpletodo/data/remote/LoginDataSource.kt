package com.mahdivajdi.simpletodo.data.remote

import com.mahdivajdi.simpletodo.data.Result
import com.mahdivajdi.simpletodo.data.handleNetworkResult
import com.mahdivajdi.simpletodo.data.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

interface LoginService {

    @POST("registration/")
    suspend fun register(@Body user: RegisterUser): Response<LoggedInUser>

    @POST("login/")
    suspend fun login(@Body user: LoginUser): Response<LoggedInUser>

    @POST("logout/")
    suspend fun logout(): Response<JSONObject>

    @GET("user/")
    suspend fun getUser(): Response<User>

    @POST("token/verify/")
    suspend fun verifyToken(token: AccessToken): Response<JSONObject>

    @GET("token/verify/")
    suspend fun refreshToken(token: RefreshToken): Response<JSONObject>

}


object LoginServiceBuilder {

    private const val url = "http://10.0.2.2:8000/dj_rest/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }
}

class LoginDataSource(private val loginService: LoginService) {

    suspend fun login(user: LoginUser): Result<LoggedInUser> =
        handleNetworkResult { loginService.login(user) }


    fun logout() {
        // TODO: revoke authentication
    }
}