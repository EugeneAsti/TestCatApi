package ru.aeyu.catapitestapp.data.remote.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.aeyu.catapitestapp.domain.models.Cat

interface CatsPagingDataRepository {
    operator fun invoke(catsPerPage: Int, breedId: String? = null): Flow<PagingData<Cat>>

}