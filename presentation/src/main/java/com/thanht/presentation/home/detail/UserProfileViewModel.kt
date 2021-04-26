package com.thanht.presentation.home.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thanht.data.executor.PostExecutionThread
import com.thanht.domain.base.Status
import com.thanht.domain.home.detail.GetUserDetailUseCase
import com.thanht.presentation.toUserModel
import com.thanht.presentation.base.BaseViewModel
import com.thanht.presentation.model.UserInfo
import com.thanht.presentation.toUserInfo
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val mPostExecutionThread: PostExecutionThread
) : BaseViewModel() {

    private val mUserInfoLive = MutableLiveData<UserInfo>()
    val userInfoLive: LiveData<UserInfo> = mUserInfoLive

    private val mErrorMsgLive = MutableLiveData<String?>()
    val errorMsgLive: LiveData<String?> = mErrorMsgLive

    val hideBioViewLive = MediatorLiveData<Boolean>().apply {
        addSource(userInfoLive) { userInfo ->
            userInfo ?: return@addSource
            value = userInfo.bio.isNullOrBlank()
        }
    }

    val hideCompanyViewLive = MediatorLiveData<Boolean>().apply {
        addSource(userInfoLive) { userInfo ->
            userInfo ?: return@addSource
            value = userInfo.company.isNullOrBlank()
        }
    }

    val hideEmailViewLive = MediatorLiveData<Boolean>().apply {
        addSource(userInfoLive) { userInfo ->
            userInfo ?: return@addSource
            value = userInfo.email.isNullOrBlank()
        }
    }

    fun setUserInfo(userInfo: UserInfo?) {
        userInfo ?: return
        mUserInfoLive.value?.userName ?: run {
            mUserInfoLive.value = userInfo
            loadUserInfo(userInfo.userName)
        }
    }

    private fun loadUserInfo(userName: String?) {
        viewModelScope.launch(mPostExecutionThread.io) {
            val result = getUserDetailUseCase.getUserDetail(userName)
            when (result.status) {
                Status.SUCCESS -> {
                    val userModel = result.data?.toUserModel()
                    mUserInfoLive.postValue(userModel?.toUserInfo())
                }
                Status.ERROR -> {
                    mErrorMsgLive.postValue(result.message)
                }
                Status.EMPTY -> {
                    mErrorMsgLive.postValue("User not found")
                }
                else -> {
                }
            }
        }
    }
}