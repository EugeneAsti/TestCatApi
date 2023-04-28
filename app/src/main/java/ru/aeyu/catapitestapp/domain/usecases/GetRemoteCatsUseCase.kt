package ru.aeyu.catapitestapp.domain.usecases

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import ru.aeyu.catapitestapp.data.remote.repositories.CatsRemoteRepository
import ru.aeyu.catapitestapp.domain.models.Cat
import java.io.IOException

class GetRemoteCatsUseCase(
    private val remoteRepository: CatsRemoteRepository
    ) {
    suspend operator fun invoke(catsQuantity: Int, breedId: String): Flow<Result<List<Cat>>> =
        remoteRepository.getCats(catsQuantity, breedId).map {
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
