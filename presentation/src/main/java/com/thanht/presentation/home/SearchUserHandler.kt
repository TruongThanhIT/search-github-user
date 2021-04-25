package com.thanht.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thanht.data.executor.PostExecutionThread
import com.thanht.domain.home.list.SearchUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

private const val DEBOUNCE_TIME = 400L
const val PAGE_SIZE = 30

class SearchUserHandler(
    private val mScope: CoroutineScope,
    private val mUseCase: SearchUserUseCase,
    private val mLoadingLive: MutableLiveData<Boolean>,
    postExecutionThread: PostExecutionThread
) {

    private val mErrorMsgLive = MutableLiveData<String?>()
    val errorMsgLive: LiveData<String?> = mErrorMsgLive

    private val mQueryStringFlow = MutableStateFlow("")
    var queryString: String
        get() = mQueryStringFlow.value
        set(value) {
            mQueryStringFlow.value = value
        }

    val searchResultFlow = mQueryStringFlow
        .debounce(DEBOUNCE_TIME)
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
        .flowOn(postExecutionThread.io)

    private fun createPagingFlow() = Pager(
        initialKey = 1,
        config = PagingConfig(PAGE_SIZE, 1, true, PAGE_SIZE),
        pagingSourceFactory = { UserPagingSource(mUseCase, queryString, mErrorMsgLive) }
    ).flow.cachedIn(mScope)

}
