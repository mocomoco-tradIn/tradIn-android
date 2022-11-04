package com.mocomoco.tradin.di

import com.mocomoco.tradin.data.data.impl.AuthRepositoryImpl
import com.mocomoco.tradin.data.data.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepositoryModule {
    @Binds
    fun provideSignupRepository(signupRepository: AuthRepositoryImpl): AuthRepository
}