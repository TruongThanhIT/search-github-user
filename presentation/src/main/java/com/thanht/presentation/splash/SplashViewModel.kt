package com.thanht.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thanht.presentation.base.BaseViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(
) : BaseViewModel() {

    private val mSplashState = MutableLiveData<SplashState>()
    var splashState: LiveData<SplashState> = mSplashState

    fun updateState(state: SplashState) {
        mSplashState.postValue(state)
    }
}

sealed class SplashState {
    object HomeActivity : SplashState()
}