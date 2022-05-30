package com.techun.memorygame.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.techun.memorygame.R
import com.techun.memorygame.databinding.ActivityRegisterBinding
import com.techun.memorygame.domain.model.UserModel
import com.techun.memorygame.ui.viewmodel.AuthViewModel
import com.techun.memorygame.utils.*
import com.techun.memorygame.utils.Constants.EMAIL_ALREADY_EXISTS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        initListener()
    }

    private fun initListener() {
        binding.btnmContinue.setOnClickListener(this)
        binding.tvTermConditions.setOnClickListener(this)
        binding.tvPrivacyPolicy.setOnClickListener(this)
        binding.tvLogin.setOnClickListener(this)
    }

    private fun initObservers() {
        var user = UserModel()
        viewModel.signUpState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<UserModel> -> {
                    user = dataState.data
                    viewModel.existUser(user.id)
                }
                is DataState.Error -> {
                    manageRegisterErrorMessages(dataState.exception)
                }
                is DataState.Loading -> {

                }
                else -> Unit
            }
        }

        viewModel.saveUserState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    goToActivity<LoginActivity>()
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
                        goToActivity<LoginActivity>()
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
            Constants.USER_NOT_EXISTS -> {
                toast(getString(R.string.login__error_user_no_registered))
            }
            Constants.WRONG_PASSWORD -> {
                toast(getString(R.string.login__error_wrong_password))
            }
            else -> {
                toast(getString(R.string.login__error_unknown_error))
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnmContinue -> {
                if (isUserDataOk()) {
                    viewModel.signUp(createUser(), binding.tieUserPassword.text.toString())
                }
            }
            R.id.tvTermConditions -> {
                Toast.makeText(this, "Show terms and conditions", Toast.LENGTH_LONG).show()
            }
            R.id.tvPrivacyPolicy -> {
                Toast.makeText(this, "Show Privacy Policy", Toast.LENGTH_LONG).show()
            }
            R.id.tvLogin -> {
                goToActivity<LoginActivity>()
            }
        }
    }

    private fun createUser(): UserModel {
        val email = binding.tieUserEmail.text.toString()
        return UserModel(
            email = email
        )
    }

    private fun isUserDataOk(): Boolean {
        return when {
            isInputEmpty(binding.tieUserEmail, true) -> false

            isPasswordInsecure() -> {
                toast(getString(R.string.signup__error_passwords_match))
                false
            }

            else -> true

        }
    }

    private fun isPasswordInsecure(): Boolean {

        return if (binding.tieUserPassword.text.toString().length <= 6) {
            toast(getString(R.string.signup__error_password_insecure))
            true
        } else {
            binding.tieUserPassword.text.toString() != binding.tieConfirmPassword.text.toString()
        }
    }

    private fun manageRegisterErrorMessages(exception: Exception) {
        if (exception.toString() == EMAIL_ALREADY_EXISTS) {
            toast(getString(R.string.signup__error_email_already_registered))
        } else {
            toast(getString(R.string.signup__error_unknown_error))
        }
    }

}