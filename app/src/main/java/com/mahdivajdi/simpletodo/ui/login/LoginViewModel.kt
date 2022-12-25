package com.mahdivajdi.simpletodo.ui.login

import android.util.Patterns
import androidx.lifecycle.*
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.LoginRepository
import com.mahdivajdi.simpletodo.data.NetworkResult
import com.mahdivajdi.simpletodo.data.UserPreferences
import com.mahdivajdi.simpletodo.data.remote.model.LoginApiResponseModel
import com.mahdivajdi.simpletodo.data.remote.model.LoginUserRequestModel
import com.mahdivajdi.simpletodo.data.remote.model.RegisterUserRequestModel
import com.mahdivajdi.simpletodo.data.remote.LoginDataSource
import com.mahdivajdi.simpletodo.data.remote.LoginServiceBuilder
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _user = MutableLiveData<NetworkResult<LoginApiResponseModel>>()
    val user: LiveData<NetworkResult<LoginApiResponseModel>> = _user

    fun login(user: LoginUserRequestModel) {
        viewModelScope.launch {
            _user.value = loginRepository.login(user)
        }
    }

    fun register(user: RegisterUserRequestModel) {
        viewModelScope.launch {
            _user.value = loginRepository.register(user)
        }
    }

    fun saveAuthTokens(refreshToken: String, accessToken: String) = viewModelScope.launch {
        loginRepository.saveAuthTokens(refreshToken, accessToken)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}


class LoginViewModelFactory(
    private val loginRepository: LoginRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}