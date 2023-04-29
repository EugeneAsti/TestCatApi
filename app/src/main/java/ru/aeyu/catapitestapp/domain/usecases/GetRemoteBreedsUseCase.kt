package ru.aeyu.catapitestapp.domain.usecases

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import ru.aeyu.catapitestapp.data.remote.repositories.CatsRemoteRepository
import ru.aeyu.catapitestapp.domain.models.Breed
import java.io.IOException

class GetRemoteBreedsUseCase(
    private val remoteRepository: CatsRemoteRepository
    ) {
    suspend operator fun invoke(): Flow<Result<List<Breed>>> =
        remoteRepository.getBreeds().map {
            Result.success(it)
        }
            .retryWhen { cause, _ ->
                if (cause is IOException) {
                    emit(Result.failure(cause))
                    delay(10000)
                    true
                } else {
                    false
                }
            }
            .catch {
                emit(Result.failure(it))
            }
}
