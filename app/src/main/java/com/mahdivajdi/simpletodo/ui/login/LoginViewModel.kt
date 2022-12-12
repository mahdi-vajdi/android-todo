package com.mahdivajdi.simpletodo.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.LoginRepository
import com.mahdivajdi.simpletodo.data.Result
import com.mahdivajdi.simpletodo.data.model.LoginUser
import com.mahdivajdi.simpletodo.data.remote.LoginDataSource
import kotlinx.coroutines.launch


class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(user: LoginUser) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            when (val response = loginRepository.login(user)) {
                is Result.Success -> {
                    Log.d("LoginOp", "login success: $response")
                    _loginResult.value = LoginResult(LoggedInUserView(user.userName))
                }
                is Result.Error -> Log.d("LoginOp", "login error: $response")
                is Result.Exception -> Log.d("LoginOp", "login exception: $response")
            }

        }
        /*if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }*/
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