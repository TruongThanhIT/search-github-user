package com.thanht.presentation.base

import androidx.lifecycle.ViewModel
import com.thanht.presentation.ext.SingleLiveData

abstract class BaseViewModel : ViewModel() {

    val progressDialogEvent = SingleLiveData<Boolean>()

    open fun showProgress() {
        progressDialogEvent.postValue(true)
    }

    open fun hideProgress() {
        progressDialogEvent.postValue(false)
    }

}
