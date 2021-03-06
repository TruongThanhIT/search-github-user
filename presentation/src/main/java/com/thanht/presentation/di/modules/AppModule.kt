package com.thanht.presentation.di.modules

import com.thanht.data.net.ApiConnection
import com.thanht.data.user.UserService
import com.thanht.data.util.provideService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[InstallIn(SingletonComponent::class) Module]
object AppModule {

    @[Provides Singleton]
    internal fun provideUsersService(apiConnection: ApiConnection): UserService {
        return apiConnection.provideService()
    }
}