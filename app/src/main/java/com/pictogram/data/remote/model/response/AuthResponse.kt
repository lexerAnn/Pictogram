package com.pictogram.data.remote.model.response

data class AuthResponse(
    var id: Int,
    var email: String,
    var auth: Auth? = null,
)