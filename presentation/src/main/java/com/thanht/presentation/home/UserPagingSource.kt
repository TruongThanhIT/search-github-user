package com.thanht.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thanht.domain.base.Status
import com.thanht.domain.home.list.SearchUserUseCase
import com.thanht.presentation.toUserModels
import com.thanht.presentation.model.UserInfo
import com.thanht.presentation.toUserInfoList

class UserPagingSource(
    private val mSearchUserUseCase: SearchUserUseCase,
    private val mQuery: String,
    private val mErrorMsgLive: MutableLiveData<String?>
) : PagingSource<Int, UserInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserInfo> = try {
        val pageNumber = params.key ?: 1
        val pageSize = params.loadSize

        val result = mSearchUserUseCase.searchUser(mQuery, pageNumber, pageSize)
        val data = when (result.status) {
            Status.SUCCESS -> {
                result.data?.items?.toUserModels() ?: emptyList()
            }
            Status.ERROR -> {
                mErrorMsgLive.postValue(result.message)
                emptyList()
            }
            else -> {
                emptyList()
            }
        }.toUserInfoList()

        val nextPage = data
            .takeIf { it.size == pageSize }
            ?.run { pageNumber + 1 }

        LoadResult.Page(data, null, nextPage)
    } catch (exception: Exception) {
        LoadResult.Error(exception)
    }

    override fun getRefreshKey(state: PagingState<Int, UserInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
