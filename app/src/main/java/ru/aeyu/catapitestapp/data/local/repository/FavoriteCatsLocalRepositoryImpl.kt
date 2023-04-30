package ru.aeyu.catapitestapp.data.local.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.aeyu.catapitestapp.data.local.data_source.CatsDao
import ru.aeyu.catapitestapp.data.local.models.CatLocal
import ru.aeyu.catapitestapp.data.mappers.toDomainModel
import ru.aeyu.catapitestapp.domain.models.Cat

class FavoriteCatsLocalRepositoryImpl(
    private val catsDao: CatsDao
) : FavoriteCatsLocalRepository{

    override suspend fun saveFavoriteCat(catLocal: CatLocal) {
        catsDao.saveCat(catLocal)
    }

    override suspend fun saveFavoriteCats(catLocal: List<CatLocal>) {
        catsDao.saveCats(catLocal)
    }

    override suspend fun getFavoriteCats(): Flow<List<Cat>> =
        catsDao.getFavoriteCatsLocal().map {list ->
            list.map {item ->
                item.toDomainModel()
            }
        }


}