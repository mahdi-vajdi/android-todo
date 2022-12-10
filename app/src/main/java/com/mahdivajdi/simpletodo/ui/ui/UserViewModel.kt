package com.mahdivajdi.simpletodo.ui.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserViewModel : ViewModel() {

    private val _userLoggedIn: LiveData<Boolean> = MutableLiveData(false)
    val userLoggedIn: LiveData<Boolean> get() = _userLoggedIn

}

/*
class UserViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}*/
