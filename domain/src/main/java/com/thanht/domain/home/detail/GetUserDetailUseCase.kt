package com.thanht.domain.home.detail

import com.thanht.domain.base.BaseResult
import com.thanht.domain.base.Status.EMPTY
import com.thanht.domain.home.UserRepository
import com.thanht.domain.response.UserDetailResponse
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun getUserDetail(
        userName: String?
    ): BaseResult<UserDetailResponse> {
        if (userName.isNullOrEmpty()) return BaseResult(EMPTY, null, null)
        return repository.getUserDetail(userName)
    }
}