package ru.aeyu.catapitestapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.aeyu.catapitestapp.data.remote.data_source.CatsRemoteApi
import ru.aeyu.catapitestapp.data.remote.repositories.CatsPagingDataRepository
import ru.aeyu.catapitestapp.data.remote.repositories.CatsPagingDataRepositoryImpl
import ru.aeyu.catapitestapp.data.remote.repositories.CatsRemoteRepository
import ru.aeyu.catapitestapp.data.remote.repositories.CatsRemoteRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RemoteCatModule {

    @Provides
    fun provideCatApi(
        retrofit: Retrofit
    ): CatsRemoteApi {
        return retrofit.create(CatsRemoteApi::class.java)
    }

    @Provides
    fun provideCatsRemoteRepository(catApi: CatsRemoteApi) : CatsRemoteRepository =
        CatsRemoteRepositoryImpl(catApi)


    @Provides
    fun provideCatsPagingRemoteRepository(catApi: CatsRemoteApi) : CatsPagingDataRepository =
        CatsPagingDataRepositoryImpl(catApi)
}
