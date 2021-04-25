package com.thanht.data.user

import com.thanht.domain.response.UserDetailResponse
import com.thanht.domain.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") name: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Response<UserResponse>

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): Response<UserDetailResponse>
}