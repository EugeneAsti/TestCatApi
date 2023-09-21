package ru.aeyu.catapitestapp.domain.usecases

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import ru.aeyu.catapitestapp.data.local.repository.FavoriteCatsLocalRepository
import ru.aeyu.catapitestapp.domain.models.Cat
import java.io.IOException

class GetFavoriteCatsUseCase(
    private val localRepository: FavoriteCatsLocalRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Cat>>> =
        localRepository.getFavoriteCats().map {
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
