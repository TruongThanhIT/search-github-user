package com.thanht.data.user

import com.thanht.data.executor.PostExecutionThread
import com.thanht.domain.response.UserResponse
import com.thanht.domain.base.BaseResult
import com.thanht.domain.response.UserDetailResponse
import com.thanht.domain.home.UserRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mUserService: UserService,
    private val mPostExecutionThread: PostExecutionThread
) : UserRepository {

    override suspend fun getUsers(
        query: String,
        page: Int,
        pageSize: Int
    ): BaseResult<UserResponse> = withContext(mPostExecutionThread.io) {
        try {
            val response = mUserService.getUsers(query, page, pageSize)
            if (response.isSuccessful) {
                BaseResult.success(response.body())
            } else {
                BaseResult.error(response.message())
            }
        } catch (ex: Throwable) {
            BaseResult.error("${ex.message}")
        }
    }

    override suspend fun getUserDetail(userName: String): BaseResult<UserDetailResponse> = withContext(mPostExecutionThread.io) {
        try {
            val response = mUserService.getUserDetail(userName)
            if (response.isSuccessful) {
                BaseResult.success(response.body())
            } else {
                BaseResult.error(response.message())
            }
        } catch (ex: Throwable) {
            BaseResult.error("${ex.message}")
        }
    }

}