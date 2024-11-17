package com.pictogram.presentation.authentication.vm

import android.content.Context
import android.text.Editable
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pictogram.R
import com.pictogram.data.remote.model.request.LoginRequest
import com.pictogram.data.remote.model.response.AuthResponse
import com.pictogram.domain.DataState
import com.pictogram.domain.UseCases
import com.pictogram.presentation.authentication.events.NavEvent
import com.pictogram.utils.BaseViewModel
import com.pictogram.utils.Validations.isEmailValidCheck
import com.pictogram.utils.Validations.isPasswordValidCheck
import com.pictogram.utils.extensions.emitFlowResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val useCases: UseCases
): BaseViewModel() {

    private val _navigationEvent = MutableSharedFlow<NavEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _loginResponse: MutableLiveData<DataState<AuthResponse>> = MutableLiveData()
    val loginResponse: LiveData<DataState<AuthResponse>> = _loginResponse

    var email = ObservableField<String>()
    var password = ObservableField<String>()

    var emailError = ObservableField<String?>()
    var passwordError = ObservableField<String?>()

    private var isEmailValid = false
    private var isPasswordValid = false


    fun onRegisterClick(view: View){
        viewModelScope.launch {
            _navigationEvent.emit(NavEvent.NavigateToRegister)
        }
    }

    fun onLoginClick(view: View) {
        val context = view.context
        val email = email.get()
        val password = password.get()

        resetErrorValidationState()

        if (validateFields(context,email, password)) {
            return
        }

        // All validations passed, proceed with login
        Timber.d("All validations passed. Email: $email")
        val loginRequest = LoginRequest(email!!, password!!)
        login(loginRequest)
    }

    fun login(requestBody: LoginRequest) = emitFlowResults(_loginResponse){
        useCases.loginUseCase(requestBody)
    }


    private fun resetErrorValidationState() {
        // Reset all error states
        emailError.set(null)
        passwordError.set(null)
    }

    private fun validateFields(context: Context, email: String?, password: String?): Boolean {
        var hasErrors = false

        // Email validation (blank check and format check)
        when {
            email.isNullOrBlank() -> {
                emailError.set(context.getString(R.string.error_invalid_entry_non_empty))
                hasErrors = true
            }
            !isEmailValid -> {
                emailError.set(context.getString(R.string.error_invalid_email))
                hasErrors = true
            }
            password.isNullOrBlank() -> {
                passwordError.set(context.getString(R.string.error_invalid_entry_non_empty))
                hasErrors = true
            }
            !isPasswordValid -> {
                passwordError.set(context.getString(R.string.error_invalid_password))
                hasErrors = true
            }
        }

        // Password validation (blank check and format check)
        when {
            password.isNullOrBlank() -> {
                passwordError.set(context.getString(R.string.error_invalid_entry_non_empty))
                hasErrors = true
            }
            !isPasswordValid -> {
                passwordError.set(context.getString(R.string.error_invalid_password))
                hasErrors = true
            }
        }

        return hasErrors
    }


    fun onFocusChanged(view: View, hasFocus: Boolean) {
        var errorStringObservable: ObservableField<String?>? = null

        when(view.id) {
            R.id.emailTIET -> {
                errorStringObservable = emailError
            }
            R.id.passwordTIET -> {
                errorStringObservable = passwordError
            }
        }
        if (hasFocus && errorStringObservable?.get() != null) {
            errorStringObservable.set(null)
        }
    }


    fun validateEntry(view: View, editable: Editable) {
        val entry = editable.toString()
        when (view.id) {
            R.id.emailTIET -> {
                email.set(entry)
                isEmailValid = isEmailValidCheck(entry)
            }
            R.id.passwordTIET -> {
                password.set(entry)
                isPasswordValid = isPasswordValidCheck(entry)
            }
        }
    }

}