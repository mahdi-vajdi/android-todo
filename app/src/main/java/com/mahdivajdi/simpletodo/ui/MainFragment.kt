package com.mahdivajdi.simpletodo.ui

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.mahdivajdi.simpletodo.data.LoginRepository
import com.mahdivajdi.simpletodo.data.UserPreferences
import com.mahdivajdi.simpletodo.data.remote.LoginDataSource
import com.mahdivajdi.simpletodo.data.remote.LoginServiceBuilder
import com.mahdivajdi.simpletodo.databinding.FragmentMainBinding
import com.mahdivajdi.simpletodo.ui.login.LoginViewModel
import com.mahdivajdi.simpletodo.ui.login.LoginViewModelFactory

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }
    private val loginViewModel: LoginViewModel by activityViewModels {
        LoginViewModelFactory(
            LoginRepository(
                LoginDataSource(LoginServiceBuilder.retrofitService),
                UserPreferences(requireContext())
            )
        )
    }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userPreferences = UserPreferences(requireContext())
        userPreferences.refreshToken.asLiveData().observe(viewLifecycleOwner) {
            binding.edittextUsername.text = it ?: "Token is null"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}