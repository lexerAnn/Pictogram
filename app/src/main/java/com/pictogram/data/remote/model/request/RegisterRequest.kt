package com.pictogram.data.remote.model.request

data class RegisterRequest(
    val email: String,
    val age: String,
    val password: String
)