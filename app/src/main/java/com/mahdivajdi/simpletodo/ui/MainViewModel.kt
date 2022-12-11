package com.mahdivajdi.simpletodo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahdivajdi.simpletodo.data.remote.LoginDataSource
import com.mahdivajdi.simpletodo.data.LoginRepository

class MainViewModel(private val loginRepository: LoginRepository): ViewModel() {

    val userLoggedIn: Boolean = loginRepository.isLoggedIn

}

class MainViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}