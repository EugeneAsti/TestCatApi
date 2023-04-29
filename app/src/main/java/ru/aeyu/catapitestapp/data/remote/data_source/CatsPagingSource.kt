package ru.aeyu.catapitestapp.data.remote.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.aeyu.catapitestapp.data.remote.models.CatApi
import ru.aeyu.catapitestapp.domain.models.Cat
import java.lang.Exception

class CatsPagingSource(
    private val theCatApi: TheCatApi,
    private val catsPerPage: Int,
    private val breedId: String?,
) : PagingSource<Int, CatApi>() {
    override fun getRefreshKey(state: PagingState<Int, CatApi>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatApi> {

        return try {
            var nextPageNumber = params.key ?: 1
            val catsList = theCatApi.getCats(limit = catsPerPage, breedId = breedId)

            nextPageNumber++
            LoadResult.Page(
                data = catsList,
                prevKey = null,
                nextKey = nextPageNumber
            )
        }catch (ex: Exception){
            LoadResult.Error(ex)
        }
    }
}