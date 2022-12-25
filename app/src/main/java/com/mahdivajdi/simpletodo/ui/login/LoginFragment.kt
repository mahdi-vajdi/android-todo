package com.mahdivajdi.simpletodo.ui.login

import androidx.lifecycle.Observer
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.LoginRepository
import com.mahdivajdi.simpletodo.data.NetworkResult
import com.mahdivajdi.simpletodo.data.UserPreferences
import com.mahdivajdi.simpletodo.data.remote.LoginDataSource
import com.mahdivajdi.simpletodo.data.remote.LoginServiceBuilder
import com.mahdivajdi.simpletodo.data.remote.model.LoginUserRequestModel
import com.mahdivajdi.simpletodo.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private lateinit var preferences: UserPreferences

    private val loginViewModel: LoginViewModel by activityViewModels {
        LoginViewModelFactory(
            LoginRepository(
                LoginDataSource(LoginServiceBuilder.retrofitService),
                preferences
            )
        )
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        preferences = UserPreferences(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText = binding.edittextUsername
        val passwordEditText = binding.edittextPassword
        val loginButton = binding.buttonLogin
        val loadingProgressBar = binding.progressbarLoading

        preferences.refreshToken.asLiveData().observe(viewLifecycleOwner) {
            if (it != null && it.isNotEmpty()) {
                view.findNavController()
                    .navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            }
        }

        binding.buttonRegister.setOnClickListener {
            view.findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.user.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                when (loginResult) {
                    is NetworkResult.Success -> {
                        Log.d("LoginOp", "login success: ${loginResult.data}")
                        loginViewModel.saveAuthTokens(loginResult.data.refreshToken, loginResult.data.accessToken)
                        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                        view.findNavController().navigate(action)
                    }
                    is NetworkResult.Error -> {
                        Log.d("LoginOp", "login error:  ${loginResult.code} ${loginResult.message}")
                        LoginResult(error = R.string.login_failed)
                    }
                    is NetworkResult.Exception -> {
                        Log.d("LoginOp", "login exception: ${loginResult.e}")
                    }
                }

                /*  loginResult.error?.let {
                      showLoginFailed(it)
                  }
                  loginResult.success?.let {
                      updateUiWithUser(it)
                      findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                  }*/
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    LoginUserRequestModel(
                        usernameEditText.text.toString(),
                        passwordEditText.text.toString()
                    )
                )
            }
            false
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(
                LoginUserRequestModel(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            )
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}