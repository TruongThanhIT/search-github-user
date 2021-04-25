package com.thanht.presentation

import com.thanht.domain.response.UserResponse
import com.thanht.domain.model.UserModel
import com.thanht.domain.response.UserDetailResponse
import com.thanht.presentation.model.UserInfo

fun UserResponse.User.toUserModel(): UserModel = UserModel(
    id = id ?: 0,
    userName = login.orEmpty(),
    avatar = avatarUrl.orEmpty()
)

fun List<UserResponse.User?>.toUserModels(): List<UserModel> =
    this.map { it?.toUserModel() ?: UserModel(0, "", "") }

fun UserDetailResponse.toUserModel(): UserModel = UserModel(
    id = id ?: 0,
    userName = login.orEmpty(),
    avatar = avatarUrl.orEmpty(),
    fullName = name.orEmpty(),
    location = location.orEmpty(),
    followers = followers ?: 0,
    following = following ?: 0,
    email = email.orEmpty(),
    company = company.orEmpty(),
    bio = bio.orEmpty()
)

fun UserModel.toUserInfo(): UserInfo =
    UserInfo(
        id = id,
        userName = userName,
        avatar = avatar,
        fullName = fullName,
        location = location,
        followers = followers,
        following = following,
        email = email,
        company = company,
        bio = bio?.trim()
    )

fun List<UserModel>.toUserInfoList(): List<UserInfo> = map { it.toUserInfo() }