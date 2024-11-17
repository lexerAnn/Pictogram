package com.pictogram.presentation.authentication.vm

import android.content.Context
import android.text.Editable
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pictogram.R
import com.pictogram.data.remote.model.request.LoginRequest
import com.pictogram.data.remote.model.request.RegisterRequest
import com.pictogram.data.remote.model.response.AuthResponse
import com.pictogram.domain.DataState
import com.pictogram.domain.UseCases
import com.pictogram.presentation.authentication.events.NavEvent
import com.pictogram.utils.BaseViewModel
import com.pictogram.utils.Validations.isAgeValidCheck
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
class RegisterViewModel  @Inject constructor(
    private val useCases: UseCases
): BaseViewModel() {

    private val _registerResponse: MutableLiveData<DataState<AuthResponse>> = MutableLiveData()
    val registerResponse: LiveData<DataState<AuthResponse>> = _registerResponse

    private val _navigationEvent = MutableSharedFlow<NavEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var age = ObservableField<String>()

    var emailError = ObservableField<String?>()
    var passwordError = ObservableField<String?>()
    var ageError = ObservableField<String?>()

    private var isEmailValid = false
    private var isPasswordValid = false
    private var isAgeValid = false


    fun onBackPress(view: View){
        viewModelScope.launch {
            _navigationEvent.emit(NavEvent.NavigateBack)
        }
    }

    fun onRegisterClick(view: View) {
        val context = view.context
        val email = email.get()
        val password = password.get()
        val age = age.get()

        resetErrorValidationState()

        if (validateFields(context, email, password, age)) {
            return
        }

        // All validations passed, proceed with login
        Timber.d("All validations passed. Email: $email")
        val request = RegisterRequest(email!!, age!!,password!!)
        register(request)
    }

    fun register(requestBody: RegisterRequest) = emitFlowResults(_registerResponse){
        useCases.registerUseCase(requestBody)
    }

    private fun resetErrorValidationState() {
        // Reset all error states
        emailError.set(null)
        passwordError.set(null)
        ageError.set(null)
    }

    private fun validateFields(context: Context, email: String?, password: String?, age: String?): Boolean {
        var hasErrors = false

        when {
            email.isNullOrBlank() -> {
                emailError.set(context.getString(R.string.error_invalid_entry_non_empty))
                hasErrors = true
            }
            !isEmailValid -> {
                emailError.set(context.getString(R.string.error_invalid_email))
                hasErrors = true
            }
        }

        when {
            age.isNullOrBlank() -> {
                ageError.set(context.getString(R.string.error_invalid_entry_non_empty))
                hasErrors = true
            }
            !isAgeValid -> {
                ageError.set(context.getString(R.string.error_invalid_age))
                hasErrors = true
            }
        }

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
        val errorField = when (view.id) {
            R.id.emailTIET -> emailError
            R.id.passwordTIET -> passwordError
            R.id.ageTIET -> ageError
            else -> null
        }

        if (hasFocus) {
            errorField?.set(null)
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

            R.id.ageTIET -> {
                age.set(entry)
                isAgeValid = isAgeValidCheck(entry)
            }
        }
    }

}