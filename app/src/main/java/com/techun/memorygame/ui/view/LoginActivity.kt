package com.techun.memorygame.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
                    progressbar(GONE)
                    manageLoginErrorMessages(dataState.exception)
                }
                else -> Unit
            }
        }

        viewModel.loginState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    progressbar(GONE)
                    goToActivity<MainActivity>(finish = true)
                }
                is DataState.Error -> {
                    progressbar(GONE)
                    manageLoginErrorMessages(dataState.exception)
                }
                else -> Unit
            }
        }

        viewModel.saveUserState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    progressbar(GONE)
                    goToActivity<MainActivity>(finish = true)
                }
                is DataState.Error -> {
                    progressbar(GONE)
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
                        progressbar(GONE)
                        goToActivity<MainActivity>(finish = true)
                    } else {
                        //Usurio no Existe
                        viewModel.saveUser(user = user)
                    }
                }
                is DataState.Error -> {
                    progressbar(GONE)
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
                toast(getString(R.string.msg_something_went_wrong))
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
                progressbar(VISIBLE)
                val email = binding.tieUserEmail.text.toString()
                val password = binding.tieUserPassword.text.toString()

                progressbar(VISIBLE)
                viewModel.login(email, password)
            }
            R.id.btnmLoginGoogle -> {
                progressbar(VISIBLE)
                signIn()
            }
            R.id.tvRegister -> {
                goToActivity<RegisterActivity>(finish = true)
            }
            R.id.tvForgotPassword -> {
                goToActivity<ForgotPasswordActivity>()
            }
        }
    }

    private fun progressbar(status: Int) {
        binding.fragmentProgressBar.visibility = status
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
        googleSignInClient.signOut()
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }
}