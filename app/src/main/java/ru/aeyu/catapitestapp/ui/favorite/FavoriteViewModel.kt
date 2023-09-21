package ru.aeyu.catapitestapp.ui.favorite

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.domain.usecases.GetFavoriteCatsUseCase
import ru.aeyu.catapitestapp.ui.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase,
) : BaseViewModel() {

    fun getCats(): Flow<List<Cat>> = flow {
        getFavoriteCatsUseCase()
            .onStart { setIsLoading(true) }
            .onCompletion {
                it?.printStackTrace()
                setIsLoading(false)
            }
            .collect { result ->
                result.onSuccess {
                    emit(it)
                }
                result.onFailure {
                    it.printStackTrace()
                    sendErrMessage("FavoriteViewModel -> ERR: ${it.localizedMessage}")
                }
            }
    }
}