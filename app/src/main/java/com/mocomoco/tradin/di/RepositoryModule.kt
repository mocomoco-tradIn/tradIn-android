package com.mocomoco.tradin.di

import com.mocomoco.tradin.data.data.repository.*
import com.mocomoco.tradin.data.data.repository.impl.*
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

    @Binds
    fun provideFeedRepository(feedRepository: FeedRepositoryImpl): FeedRepository

    @Binds
    fun provideLocationRepository(locationRepository: LocationRepositoryImpl): LocationRepository

    @Binds
    fun provideSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

    @Binds
    fun provideTradeRepository(tradeRepository: TradeRepositoryImpl): TradeRepository
}
