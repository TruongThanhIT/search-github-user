package com.thanht.presentation.di.modules


import com.thanht.data.cache.AppSharePref
import com.thanht.data.net.ApiConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[InstallIn(SingletonComponent::class) Module]
class NetworkModule {
    @[Provides Singleton]
    internal fun provideApiConnection(appSharePref: AppSharePref): ApiConnection {
        return ApiConnection(appSharePref)
    }
}
