package ru.aeyu.catapitestapp.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.aeyu.catapitestapp.data.remote.repositories.CatsPagingDataRepository
import ru.aeyu.catapitestapp.data.remote.repositories.CatsRemoteRepository
import ru.aeyu.catapitestapp.domain.usecases.GetPagingCatsRemoteUseCase
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteCatsUseCase

@Module
@InstallIn(ViewModelComponent::class)
object RepositoriesModule {

    @Provides
    @ViewModelScoped
    fun provideGetRemoteCatsUseCase(
        remoteRepository: CatsRemoteRepository): GetRemoteCatsUseCase =
        GetRemoteCatsUseCase(remoteRepository)

    @Provides
    @ViewModelScoped
    fun provideGetPagingRemoteCatsUseCase(
        pagingRemoteRepository: CatsPagingDataRepository): GetPagingCatsRemoteUseCase =
        GetPagingCatsRemoteUseCase(pagingRemoteRepository)

}