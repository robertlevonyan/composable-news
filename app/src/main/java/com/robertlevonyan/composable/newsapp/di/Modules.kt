package com.robertlevonyan.composable.newsapp.di

import com.robertlevonyan.composable.newsapp.data.repository.NewsRepositoryImpl
import com.robertlevonyan.composable.newsapp.domain.repository.NewsRepository
import com.robertlevonyan.composable.newsapp.domain.usecase.NewsUseCase
import com.robertlevonyan.composable.newsapp.domain.usecase.NewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModules {
    @Binds
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModules {
    @Binds
    abstract fun bindNewsUseCase(newsUseCaseImpl: NewsUseCaseImpl): NewsUseCase
}
