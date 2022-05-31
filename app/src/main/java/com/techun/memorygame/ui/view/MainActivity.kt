package com.techun.memorygame.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.techun.memorygame.R
import com.techun.memorygame.databinding.ActivityMainBinding
import com.techun.memorygame.ui.viewmodel.AuthViewModel
import com.techun.memorygame.utils.DataState
import com.techun.memorygame.utils.goToActivity
import com.techun.memorygame.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var auth: FirebaseAuth

    private val viewModel: AuthViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        checkUser(currentUser)
    }

    private fun checkUser(currentUser: FirebaseUser?) {
        if (currentUser == null) {
            goToActivity<LoginActivity>(finish = true)
        } else {
            viewModel.userDataState.observe(this) { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                    }
                    is DataState.Error -> {
                        toast(getString(R.string.msg_error_getting_user_data))
                        goToActivity<LoginActivity>(finish = true)
                    }
                    else -> Unit
                }
            }
            viewModel.getUserData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }
}