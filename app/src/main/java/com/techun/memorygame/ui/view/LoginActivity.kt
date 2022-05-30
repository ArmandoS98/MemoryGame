package com.techun.memorygame.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.techun.memorygame.R
import com.techun.memorygame.databinding.ActivityLoginBinding
import com.techun.memorygame.domain.model.UserModel
import com.techun.memorygame.ui.viewmodel.AuthViewModel
import com.techun.memorygame.utils.Constants.GOOGLE_SIGN_IN
import com.techun.memorygame.utils.Constants.USER_NOT_EXISTS
import com.techun.memorygame.utils.Constants.WRONG_PASSWORD
import com.techun.memorygame.utils.DataState
import com.techun.memorygame.utils.goToActivity
import com.techun.memorygame.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        initListener()
    }

    private fun initObservers() {
        var user = UserModel()
        viewModel.loginGoogleState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    user = dataState.data
                    viewModel.existUser(user.id)
                }
                is DataState.Error -> {
                    manageLoginErrorMessages(dataState.exception)
                }
                else -> Unit
            }
        }

        viewModel.loginState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    goToActivity<MainActivity>()
                }
                is DataState.Error -> {
                    manageLoginErrorMessages(dataState.exception)
                }
                else -> Unit
            }
        }

        viewModel.saveUserState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    goToActivity<MainActivity>()
                }
                is DataState.Error -> {
                    manageLoginErrorMessages(dataState.exception)
                }
                else -> Unit
            }
        }

        viewModel.existUserState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    val result = dataState.data
                    if (result) {
                        //Usuario Existe
                        goToActivity<MainActivity>()
                    } else {
                        //Usurio no Existe
                        viewModel.saveUser(user = user)
                    }
                }
                is DataState.Error -> {
                    manageLoginErrorMessages(dataState.exception)
                }
                else -> Unit
            }
        }
    }

    private fun manageLoginErrorMessages(exception: Exception) {
        when (exception.message) {
            USER_NOT_EXISTS -> {
                toast(getString(R.string.login__error_user_no_registered))
            }
            WRONG_PASSWORD -> {
                toast(getString(R.string.login__error_wrong_password))
            }
            else -> {
                toast(getString(R.string.login__error_unknown_error))
            }
        }
    }

    private fun initListener() {
        binding.btnmLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
        binding.btnmLoginGoogle.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnmLogin -> {
                val email = binding.tieUserEmail.text.toString()
                val password = binding.tieUserPassword.text.toString()

                viewModel.login(email, password)
            }
            R.id.btnmLoginGoogle -> {
                signIn()
            }
            R.id.tvRegister -> {
                goToActivity<RegisterActivity>()
            }
            R.id.tvForgotPassword -> {
                goToActivity<ForgotPasswordActivity>()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.loginWithGoogle(account!!)
            } catch (e: ApiException) {
                val error = e.message
                toast(error.let { it ?: "unknow" })
            }
        }
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }
}