package com.mahdivajdi.simpletodo.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.LoginRepository
import com.mahdivajdi.simpletodo.data.NetworkResult
import com.mahdivajdi.simpletodo.data.UserPreferences
import com.mahdivajdi.simpletodo.data.remote.LoginDataSource
import com.mahdivajdi.simpletodo.data.remote.LoginServiceBuilder
import com.mahdivajdi.simpletodo.data.remote.model.RegisterUserRequestModel
import com.mahdivajdi.simpletodo.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private lateinit var preferences: UserPreferences

    private val loginViewModel: LoginViewModel by activityViewModels {
        LoginViewModelFactory(
            LoginRepository(
                LoginDataSource(LoginServiceBuilder.retrofitService),
                preferences
            )
        )
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        preferences = UserPreferences(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences.refreshToken.asLiveData().observe(viewLifecycleOwner) {
            if (it != null && it.isNotEmpty()) {
                view.findNavController()
                    .navigate(RegisterFragmentDirections.actionRegisterFragmentToMainFragment())
            }
        }

        binding.buttonRegisterRegister.setOnClickListener {
            loginViewModel.register(
                RegisterUserRequestModel(
                    userName = binding.edittextRegisterUsername.text.toString(),
                    password = binding.edittextRegisterPassword.text.toString(),
                    passwordRepeat = binding.edittextRegisterPasswordRepeat.text.toString()
                )
            )
        }

        loginViewModel.user.observe(viewLifecycleOwner) { loginResult ->
            when (loginResult) {
                is NetworkResult.Success -> {
                    Log.d("LoginOp", "login success: ${loginResult.data}")
                    loginViewModel.saveAuthTokens(loginResult.data.refreshToken,
                        loginResult.data.accessToken)

                    view.findNavController()
                        .navigate(RegisterFragmentDirections.actionRegisterFragmentToMainFragment())
                }
                is NetworkResult.Error -> {
                    Log.d("LoginOp", "login error:  ${loginResult.code} ${loginResult.message}")
                    LoginResult(error = R.string.login_failed)
                }
                is NetworkResult.Exception -> {
                    Log.d("LoginOp", "login exception: ${loginResult.e}")
                }
            }
        }
    }

}