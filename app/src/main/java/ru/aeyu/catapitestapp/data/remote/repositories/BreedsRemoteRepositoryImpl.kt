package ru.aeyu.catapitestapp.data.remote.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.aeyu.catapitestapp.data.mappers.toDomainModel
import ru.aeyu.catapitestapp.data.remote.data_source.BreedsRemoteApi
import ru.aeyu.catapitestapp.domain.models.Breed

class BreedsRemoteRepositoryImpl(private val breedsRemoteApi: BreedsRemoteApi)
    : BreedsRemoteRepository {


    override suspend fun getBreeds(): Flow<List<Breed>> =
        flowOf(
            try {
                breedsRemoteApi.getBreeds()
            }catch (ex: Exception){
             listOf()
            }
        ).map { item ->
            val list = item.map {
                it.toDomainModel()
            }.toMutableList()
            list.add(0, Breed())
            list.toList()
        }

}