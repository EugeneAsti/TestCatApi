package ru.aeyu.catapitestapp.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.aeyu.catapitestapp.data.local.repository.FavoriteCatsLocalRepository
import ru.aeyu.catapitestapp.data.remote.repositories.BreedsRemoteRepository
import ru.aeyu.catapitestapp.data.remote.repositories.CatsPagingDataRepository
import ru.aeyu.catapitestapp.data.remote.repositories.CatsRemoteRepository
import ru.aeyu.catapitestapp.data.remote.repositories.FavoritesRemoteRepository
import ru.aeyu.catapitestapp.domain.usecases.GetFavoriteCatsUseCase
import ru.aeyu.catapitestapp.domain.usecases.GetPagingCatsRemoteUseCase
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteBreedsUseCase
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteCatUseCase
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteCatsUseCase
import ru.aeyu.catapitestapp.domain.usecases.SetFavoriteCatUseCase

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

    @Provides
    @ViewModelScoped
    fun provideGetRemoteCatUseCase(
        remoteRepository: CatsRemoteRepository): GetRemoteCatUseCase =
        GetRemoteCatUseCase(remoteRepository)

    @Provides
    @ViewModelScoped
    fun provideGetRemoteBreedsUseCase(
        breedsRemoteRepository: BreedsRemoteRepository): GetRemoteBreedsUseCase =
        GetRemoteBreedsUseCase(breedsRemoteRepository)

    @Provides
    @ViewModelScoped
    fun provideSetFavoriteImageUseCase(
        remoteRepository: FavoritesRemoteRepository,
        localRepository: FavoriteCatsLocalRepository): SetFavoriteCatUseCase =
        SetFavoriteCatUseCase(remoteRepository, localRepository)

    @Provides
    @ViewModelScoped
    fun provideGetFavoriteCatsUseCase(
//        remoteRepository: FavoritesRemoteRepository,
        localRepository: FavoriteCatsLocalRepository
    ): GetFavoriteCatsUseCase =
        GetFavoriteCatsUseCase(localRepository)

}