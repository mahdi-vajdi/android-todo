package com.mahdivajdi.simpletodo.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.AuthRepository
import com.mahdivajdi.simpletodo.data.local.UserPreferences
import com.mahdivajdi.simpletodo.data.remote.AuthDataSource
import com.mahdivajdi.simpletodo.data.remote.AuthServiceBuilder
import com.mahdivajdi.simpletodo.data.remote.NetworkResult
import com.mahdivajdi.simpletodo.data.remote.model.RegisterUserRequestModel
import com.mahdivajdi.simpletodo.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var preferences: UserPreferences

    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(
            AuthRepository(
                AuthDataSource(AuthServiceBuilder.retrofitService),
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
            authViewModel.register(
                RegisterUserRequestModel(
                    userName = binding.edittextRegisterUsername.text.toString(),
                    password = binding.edittextRegisterPassword.text.toString(),
                    passwordRepeat = binding.edittextRegisterPasswordRepeat.text.toString()
                )
            )
        }

        authViewModel.user.observe(viewLifecycleOwner) { loginResult ->
            when (loginResult) {
                is NetworkResult.Success -> {
                    Log.d("LoginOp", "login success: ${loginResult.data}")
                    authViewModel.saveAuthTokens(loginResult.data.refreshToken,
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