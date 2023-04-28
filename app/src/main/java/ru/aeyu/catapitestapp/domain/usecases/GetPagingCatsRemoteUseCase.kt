package ru.aeyu.catapitestapp.domain.usecases

import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.aeyu.catapitestapp.data.remote.repositories.CatsPagingDataRepository
import ru.aeyu.catapitestapp.domain.models.Cat

class GetPagingCatsRemoteUseCase(
    private val catsPagingDataRepository: CatsPagingDataRepository
) {
    operator fun invoke(scope: CoroutineScope, catsPerPage: Int,
                        breedId: String? = null): Flow<PagingData<Cat>> =
        catsPagingDataRepository(catsPerPage, breedId)
        .cachedIn(scope)

}