package ru.aeyu.catapitestapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.aeyu.catapitestapp.data.remote.data_source.BreedsRemoteApi
import ru.aeyu.catapitestapp.data.remote.repositories.BreedsRemoteRepository
import ru.aeyu.catapitestapp.data.remote.repositories.BreedsRemoteRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RemoteBreedModule {

    @Provides
    fun provideBreedApi(
        retrofit: Retrofit
    ): BreedsRemoteApi {
        return retrofit.create(BreedsRemoteApi::class.java)
    }

    @Provides
    fun provideBreedsRemoteRepository(breedsRemoteApi: BreedsRemoteApi) : BreedsRemoteRepository =
        BreedsRemoteRepositoryImpl(breedsRemoteApi)

}