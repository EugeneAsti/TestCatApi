package ru.aeyu.catapitestapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.aeyu.catapitestapp.data.local.repository.FavoriteCatsLocalRepository
import ru.aeyu.catapitestapp.data.mappers.toLocalModel
import ru.aeyu.catapitestapp.data.remote.models.FavoriteReq
import ru.aeyu.catapitestapp.data.remote.repositories.FavoritesRemoteRepository
import ru.aeyu.catapitestapp.domain.models.Cat

class SetFavoriteCatUseCase(
    private val remoteRepository: FavoritesRemoteRepository,
    private val localRepository: FavoriteCatsLocalRepository
) {
    suspend operator fun invoke(cat: Cat, userId: String): Flow<Result<Boolean>> =
        remoteRepository.setFavoriteImage(
            favoriteReq = FavoriteReq(cat.id, userId)
        ).map {result ->
            if(result)
                localRepository.saveFavoriteCat(cat.toLocalModel())
            Result.success(result)
        }
            .catch {
                emit(Result.failure(it))
            }

}