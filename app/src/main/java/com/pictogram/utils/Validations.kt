package com.pictogram.utils

object Validations {

    fun isEmailValidCheck(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun isPasswordValidCheck(password: String): Boolean {
        return password.length in 6..12
    }

     fun isAgeValidCheck(age: String): Boolean {
        return try {
            val ageInt = age.toInt()
            ageInt in 18..99
        } catch (e: NumberFormatException) {
            false
        }
    }
}