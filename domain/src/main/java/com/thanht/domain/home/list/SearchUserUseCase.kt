package com.thanht.domain.home.list

import com.thanht.domain.base.BaseResult
import com.thanht.domain.home.UserRepository
import com.thanht.domain.response.UserResponse
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun searchUser(
        query: String,
        page: Int,
        pageSize: Int
    ): BaseResult<UserResponse> = repository.getUsers(query, page, pageSize)
}