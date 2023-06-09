package ru.aeyu.catapitestapp.domain.usecases

import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import ru.aeyu.catapitestapp.data.remote.repositories.CatsPagingDataRepository
import ru.aeyu.catapitestapp.domain.models.Cat
import java.io.IOException

class GetPagingCatsRemoteUseCase(
    private val catsPagingDataRepository: CatsPagingDataRepository
) {
    operator fun invoke(scope: CoroutineScope, catsPerPage: Int,
                        breedId: String? = null): Flow<Result<PagingData<Cat>>> =
        catsPagingDataRepository(catsPerPage, breedId)
            .cachedIn(scope)
            .map {
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