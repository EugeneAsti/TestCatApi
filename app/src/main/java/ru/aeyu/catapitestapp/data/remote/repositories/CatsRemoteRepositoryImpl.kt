package ru.aeyu.catapitestapp.data.remote.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.aeyu.catapitestapp.data.mappers.toDomainModel
import ru.aeyu.catapitestapp.data.remote.data_source.TheCatApi
import ru.aeyu.catapitestapp.domain.models.Breed
import ru.aeyu.catapitestapp.domain.models.Cat

class CatsRemoteRepositoryImpl(private val theCatApi: TheCatApi)
    : CatsRemoteRepository {
    override suspend fun getCats(catsQuantity: Int, breedId: String?): Flow<List<Cat>> =
        flowOf(theCatApi.getCats(limit = catsQuantity, breedId = breedId)).map {item ->
            item.map {
                it.toDomainModel()
            }
        }

    override suspend fun getCat(catImageId: String): Flow<Cat> =
        flowOf(theCatApi.getCat(catImageId)).map {item ->
            item.toDomainModel()
        }

    override suspend fun getBreeds(): Flow<List<Breed>> =
        flowOf(theCatApi.getBreeds()).map {item ->
            item.map {
                it.toDomainModel()
            }
        }
}