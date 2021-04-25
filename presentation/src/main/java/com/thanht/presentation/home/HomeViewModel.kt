package com.thanht.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.thanht.data.executor.PostExecutionThread
import com.thanht.domain.home.list.SearchUserUseCase
import com.thanht.presentation.base.BaseViewModel
import com.thanht.presentation.model.UserInfo
import javax.inject.Inject

const val MIN_SEARCH_LENGTH = 1

class HomeViewModel @Inject constructor(
    private val mSearchUserUseCase: SearchUserUseCase,
    mPostExecutionThread: PostExecutionThread
) : BaseViewModel() {

    private val mIsLoadingLive = MutableLiveData(false)
    val isLoadingLive: LiveData<Boolean> = mIsLoadingLive

    fun queryWeather(query: CharSequence?) {
        val iQuery = query?.toString()?.trim().orEmpty()
        if (iQuery.length < MIN_SEARCH_LENGTH || iQuery == searchHandler.queryString) return
        searchHandler.queryString = query?.toString()?.trim().orEmpty()
    }

    private val searchHandler by lazy {
        SearchUserHandler(
            viewModelScope,
            mSearchUserUseCase,
            mIsLoadingLive,
            mPostExecutionThread
        )
    }

    val searchUserResultLive: LiveData<PagingData<UserInfo>>
        get() = searchHandler.searchResultFlow.asLiveData()

    val searchUserErrorMsg: LiveData<String?>
        get() = searchHandler.errorMsgLive
}