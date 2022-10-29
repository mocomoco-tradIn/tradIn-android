package com.mocomoco.tradin.di

import com.mocomoco.tradin.data.data.impl.SignupRepositoryImpl
import com.mocomoco.tradin.data.data.repository.SignupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepositoryModule {
    @Binds
    fun provideSignupRepository(signupRepository: SignupRepositoryImpl): SignupRepository
}