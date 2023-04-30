package ru.aeyu.catapitestapp.data.remote.repositories

import kotlinx.coroutines.flow.Flow
import ru.aeyu.catapitestapp.domain.models.Breed

interface BreedsRemoteRepository {

    suspend fun getBreeds(): Flow<List<Breed>>
}