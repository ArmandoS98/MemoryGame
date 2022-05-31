package com.techun.memorygame.ui.view

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.techun.memorygame.R
import com.techun.memorygame.databinding.ActivityForgotPasswordBinding
import com.techun.memorygame.domain.model.UserModel
import com.techun.memorygame.ui.viewmodel.AuthViewModel
import com.techun.memorygame.utils.DataState
import com.techun.memorygame.utils.goToActivity
import com.techun.memorygame.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        initListener()
    }

    private fun initListener() {
        binding.btnmSubmit.setOnClickListener(this)
    }

    private fun initObservers() {
        viewModel.resetPasswordState.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success<Boolean> -> {
                    progressbar(GONE)
                    toast(getString(R.string.reset_email_sent))
                    goToActivity<LoginActivity>(finish = true)
                }
                is DataState.Error -> {
                    progressbar(GONE)
                    toast(getString(R.string.msg_something_went_wrong))
                }
                is DataState.Loading -> {

                }
                else -> Unit
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnmSubmit -> {
                progressbar(VISIBLE)
                val email = binding.tieUserEmail.text.toString()
                viewModel.resetPasswordByEmail(email)
            }
        }
    }

    private fun progressbar(status: Int) {
        binding.fragmentProgressBar.visibility = status
    }
}