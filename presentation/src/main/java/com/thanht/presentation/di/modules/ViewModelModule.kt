package com.thanht.presentation.di.modules

import androidx.lifecycle.ViewModel
import com.thanht.presentation.MainViewModel
import com.thanht.presentation.di.mapkeys.ViewModelKey
import com.thanht.presentation.home.HomeViewModel
import com.thanht.presentation.home.detail.UserProfileViewModel
import com.thanht.presentation.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@[InstallIn(SingletonComponent::class) Module]
interface ViewModelModule {
    @get:[Binds IntoMap ViewModelKey(HomeViewModel::class)]
    val HomeViewModel.homeViewModel: ViewModel

    @get:[Binds IntoMap ViewModelKey(SplashViewModel::class)]
    val SplashViewModel.splashViewModel: ViewModel

    @get:[Binds IntoMap ViewModelKey(UserProfileViewModel::class)]
    val UserProfileViewModel.userProfileViewModel: ViewModel
}