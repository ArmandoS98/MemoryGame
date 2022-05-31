package com.techun.memorygame.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.techun.memorygame.domain.model.UserModel
import com.techun.memorygame.domain.usecases.auth.google.LoginWithGoogleUseCase
import com.techun.memorygame.domain.usecases.auth.login.GetUserDataUseCase
import com.techun.memorygame.domain.usecases.auth.login.LoginUseCase
import com.techun.memorygame.domain.usecases.auth.logout.LogOutUseCase
import com.techun.memorygame.domain.usecases.auth.passwordReset.SendPasswordResetEmailUseCase
import com.techun.memorygame.domain.usecases.auth.signup.ExistUserUseCase
import com.techun.memorygame.domain.usecases.auth.signup.SaveUserUseCase
import com.techun.memorygame.domain.usecases.auth.signup.SignUpUseCase
import com.techun.memorygame.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val userExistUserUseCase: ExistUserUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val resetPassword: SendPasswordResetEmailUseCase
) : ViewModel() {

    private val _loginState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val loginState: LiveData<DataState<Boolean>>
        get() = _loginState

    private val _loginGoogleState: MutableLiveData<DataState<UserModel>> = MutableLiveData()
    val loginGoogleState: LiveData<DataState<UserModel>>
        get() = _loginGoogleState

    private val _userDataState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val userDataState: LiveData<DataState<Boolean>>
        get() = _userDataState

    private val _logOutState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val logOutState: LiveData<DataState<Boolean>>
        get() = _logOutState

    private val _saveUserState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val saveUserState: LiveData<DataState<Boolean>>
        get() = _saveUserState

    private val _existUserState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val existUserState: LiveData<DataState<Boolean>>
        get() = _existUserState

    private val _signUpState: MutableLiveData<DataState<UserModel>> = MutableLiveData()
    val signUpState: LiveData<DataState<UserModel>>
        get() = _signUpState

    private val _resetPasswordState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val resetPasswordState: LiveData<DataState<Boolean>>
        get() = _resetPasswordState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password)
                .onEach { dataState ->
                    _loginState.value = dataState
                }.launchIn(viewModelScope)
        }
    }


    fun loginWithGoogle(acct: GoogleSignInAccount) = viewModelScope.launch {
        loginWithGoogleUseCase(acct)
            .onEach { dataState ->
                _loginGoogleState.value = dataState
            }.launchIn(viewModelScope)
    }

    fun getUserData() = viewModelScope.launch {
        getUserDataUseCase()
            .onEach { dataState ->
                _userDataState.value = dataState
            }.launchIn(viewModelScope)
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
                .onEach { dataState ->
                    _logOutState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            saveUserUseCase(user)
                .onEach { dataState ->
                    _saveUserState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun existUser(id: String) {
        viewModelScope.launch {
            userExistUserUseCase(id)
                .onEach { dataState ->
                    _existUserState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun signUp(user: UserModel, password: String) {
        viewModelScope.launch {
            signUpUseCase(user, password)
                .onEach { dataState ->
                    _signUpState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun resetPasswordByEmail(email: String) = viewModelScope.launch {
        resetPassword(email)
            .onEach { dataState ->
                _resetPasswordState.value = dataState
            }.launchIn(viewModelScope)
    }
}