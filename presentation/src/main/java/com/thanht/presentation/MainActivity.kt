package com.thanht.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.thanht.presentation.base.BaseActivity
import com.thanht.presentation.base.BaseViewModel
import com.thanht.presentation.databinding.ActivityMainBinding
import com.thanht.presentation.navigation.NavigationDispatcherImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val navController by lazy { findNavController(R.id.navHostFragment) }

    override val navDispatcher by lazy { NavigationDispatcherImpl(navController) }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val mViewModel: MainViewModel by viewModels { factory }
    override val viewModel: BaseViewModel
        get() = mViewModel

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}