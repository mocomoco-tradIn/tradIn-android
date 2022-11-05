package com.mocomoco.tradin.di

import com.mocomoco.tradin.data.data.repository.AuthRepository
import com.mocomoco.tradin.data.data.repository.ProductRepository
import com.mocomoco.tradin.data.data.repository.RefreshTokenRepository
import com.mocomoco.tradin.data.data.repository.impl.AuthRepositoryImpl
import com.mocomoco.tradin.data.data.repository.impl.ProductRepositoryImpl
import com.mocomoco.tradin.data.data.repository.impl.RefreshTokenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepositoryModule {
    @Binds
    fun provideSignupRepository(signupRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideRefreshTokenRepository(refreshTokenRepository: RefreshTokenRepositoryImpl): RefreshTokenRepository

    @Binds
    fun provideProductRepository(productRepository: ProductRepositoryImpl): ProductRepository


}
