package com.thanht.presentation.di.modules

import com.thanht.data.user.UserRepositoryImpl
import com.thanht.domain.home.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[InstallIn(SingletonComponent::class) Module]
abstract class UserModule {

    // repository
    @Binds
    abstract fun UserRepositoryImpl.bindUserRepository(): UserRepository
}