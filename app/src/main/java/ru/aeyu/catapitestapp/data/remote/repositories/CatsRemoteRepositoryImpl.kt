package ru.aeyu.catapitestapp.data.remote.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.aeyu.catapitestapp.data.mappers.toDomainModel
import ru.aeyu.catapitestapp.data.remote.data_source.TheCatApi
import ru.aeyu.catapitestapp.domain.models.Cat

class CatsRemoteRepositoryImpl(private val theCatApi: TheCatApi)
    : CatsRemoteRepository {
    override suspend fun getCats(catsQuantity: Int, breedId: String?): Flow<List<Cat>> =
        flowOf(theCatApi.getCats(catsQuantity, breedId).map {item ->
            item.toDomainModel()
        })

}