package com.thanht.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thanht.data.executor.PostExecutionThread
import com.thanht.domain.home.list.SearchUserUseCase
import com.thanht.presentation.model.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

private const val DEBOUNCE_TIME = 400L
const val PAGE_SIZE = 30
const val MIN_SEARCH_LENGTH = 1

class SearchUserHandler(
    private val mScope: CoroutineScope,
    private val mUseCase: SearchUserUseCase,
    private val mLoadingLive: MutableLiveData<Boolean>,
    postExecutionThread: PostExecutionThread
) {

    private val mQueryStringFlow = MutableStateFlow("")
    var queryString: String
        get() = mQueryStringFlow.value
        set(value) {
            if (value.length < MIN_SEARCH_LENGTH) return
            mQueryStringFlow.value = value
        }

    private val searchResultFlow = mQueryStringFlow
        .debounce(DEBOUNCE_TIME)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            mLoadingLive.postValue(true)
            if (query.isNotBlank()) {
                createPagingFlow()
            } else {
                mLoadingLive.postValue(false)
                flowOf(PagingData.empty())
            }
        }
        .map { response ->
            mLoadingLive.postValue(false)
            response
        }
        .onEach {
            mSearchUserResultLive.postValue(it)
        }
        .flowOn(postExecutionThread.io)

    init {
        searchResultFlow.launchIn(mScope)
    }

    private val mErrorMsgLive = MutableLiveData<String?>()
    val errorMsgLive: LiveData<String?> = mErrorMsgLive

    private val mSearchUserResultLive = MutableLiveData<PagingData<UserInfo>>()
    val searchUserResultLive: LiveData<PagingData<UserInfo>> = mSearchUserResultLive

    private fun createPagingFlow() = Pager(
        initialKey = 1,
        config = PagingConfig(PAGE_SIZE, 1, true, PAGE_SIZE),
        pagingSourceFactory = { UserPagingSource(mUseCase, queryString, mErrorMsgLive) }
    ).flow.cachedIn(mScope)

}
