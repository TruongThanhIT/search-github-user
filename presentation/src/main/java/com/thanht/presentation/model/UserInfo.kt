package com.thanht.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
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
) : Parcelable