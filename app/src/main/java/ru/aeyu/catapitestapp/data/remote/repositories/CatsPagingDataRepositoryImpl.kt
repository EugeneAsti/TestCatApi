package ru.aeyu.catapitestapp.data.remote.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.aeyu.catapitestapp.data.mappers.toDomainModel
import ru.aeyu.catapitestapp.data.remote.data_source.CatsPagingSource
import ru.aeyu.catapitestapp.data.remote.data_source.TheCatApi
import ru.aeyu.catapitestapp.domain.models.Cat

class CatsPagingDataRepositoryImpl(
    private val theCatApi: TheCatApi
) : CatsPagingDataRepository {
    override operator fun invoke(catsPerPage: Int, breedId: String?): Flow<PagingData<Cat>> =
            Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 20)
        ) {
            CatsPagingSource(theCatApi = theCatApi, catsPerPage = catsPerPage, breedId = breedId)
        }.flow.map { pagingData ->
            pagingData.map { it ->
                it.toDomainModel()
            }
        }


}