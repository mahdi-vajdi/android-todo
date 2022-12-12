package com.mahdivajdi.simpletodo.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahdivajdi.simpletodo.data.remote.LoginDataSource
import com.mahdivajdi.simpletodo.data.LoginRepository
import com.mahdivajdi.simpletodo.data.remote.LoginServiceBuilder

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(LoginServiceBuilder.retrofitService)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}