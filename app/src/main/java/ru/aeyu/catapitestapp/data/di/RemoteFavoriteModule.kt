package ru.aeyu.catapitestapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.aeyu.catapitestapp.data.remote.data_source.FavoritesRemoteApi
import ru.aeyu.catapitestapp.data.remote.repositories.FavoritesRemoteRepository
import ru.aeyu.catapitestapp.data.remote.repositories.FavoritesRemoteRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RemoteFavoriteModule {

    @Provides
    fun provideFavoriteApi(
        retrofit: Retrofit
    ): FavoritesRemoteApi {
        return retrofit.create(FavoritesRemoteApi::class.java)
    }

    @Provides
    fun provideFavoritesRemoteRepository(favoritesRemoteRepository: FavoritesRemoteApi) : FavoritesRemoteRepository =
        FavoritesRemoteRepositoryImpl(favoritesRemoteRepository)

}