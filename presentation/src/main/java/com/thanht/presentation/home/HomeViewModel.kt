package com.thanht.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thanht.data.executor.PostExecutionThread
import com.thanht.domain.home.list.SearchUserUseCase
import com.thanht.presentation.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val mSearchUserUseCase: SearchUserUseCase,
    mPostExecutionThread: PostExecutionThread
) : BaseViewModel() {

    private val mIsLoadingLive = MutableLiveData(false)
    val isLoadingLive: LiveData<Boolean> = mIsLoadingLive

    fun queryWeather(query: CharSequence?) {
        searchHandler.queryString = query?.toString()?.trim().orEmpty()
    }

    val searchHandler by lazy {
        SearchUserHandler(
            viewModelScope,
            mSearchUserUseCase,
            mIsLoadingLive,
            mPostExecutionThread
        )
    }
}