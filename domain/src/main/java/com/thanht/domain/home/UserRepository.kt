package com.thanht.domain.home

import com.thanht.domain.base.BaseResult
import com.thanht.domain.response.UserDetailResponse
import com.thanht.domain.response.UserResponse

interface UserRepository {
    suspend fun getUsers(
        query: String,
        page: Int,
        pageSize: Int
    ): BaseResult<UserResponse>

    suspend fun getUserDetail(
        userName: String
    ): BaseResult<UserDetailResponse>
}