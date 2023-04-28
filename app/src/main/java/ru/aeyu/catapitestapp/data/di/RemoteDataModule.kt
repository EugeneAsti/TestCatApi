package ru.aeyu.catapitestapp.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.aeyu.catapitestapp.data.remote.data_source.TheCatApi
import ru.aeyu.catapitestapp.data.remote.repositories.CatsPagingDataRepository
import ru.aeyu.catapitestapp.data.remote.repositories.CatsPagingDataRepositoryImpl
import ru.aeyu.catapitestapp.data.remote.repositories.CatsRemoteRepository
import ru.aeyu.catapitestapp.data.remote.repositories.CatsRemoteRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    fun provideCatApi(
        retrofit: Retrofit
    ): TheCatApi {
        return retrofit.create(TheCatApi::class.java)
    }

    @Provides
    fun provideCatsRemoteRepository(catApi: TheCatApi) : CatsRemoteRepository =
        CatsRemoteRepositoryImpl(catApi)

    @Provides
    fun provideCatsPagingRemoteRepository(catApi: TheCatApi) : CatsPagingDataRepository =
        CatsPagingDataRepositoryImpl(catApi)
}

//@Module
//@InstallIn(ViewModelComponent::class)
//object BindGetReceiptModel {
//
//    @Provides
//    @ViewModelScoped
//    fun provideGetReceiptModelUseCase(
//        getReceiptInfoRemoteRepository: ReceiptRemoteRepository
//    ): GetReceiptModelUseCase {
//        return GetReceiptModelUseCaseImpl(getReceiptInfoRemoteRepository)
//    }
//}