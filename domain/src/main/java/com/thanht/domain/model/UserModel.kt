package com.thanht.domain.model

class UserModel(
    val id: Int,
    val userName: String,
    val avatar: String,
    val fullName: String? = "",
    val location: String? = "",
    val followers: Int? = 0,
    val following: Int? = 0,
    val email: String? = "",
    val company: String? = "",
    val bio: String? = ""
)