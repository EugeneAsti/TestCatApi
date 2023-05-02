package ru.aeyu.catapitestapp.ui.favorite

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import ru.aeyu.catapitestapp.data.remote.repositories.PreferencesDelegate
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.domain.usecases.GetFavoriteCatsUseCase
import ru.aeyu.catapitestapp.ui.home.HomeViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase,
    preferences: SharedPreferences
) : ViewModel() {

    private val userId: String by PreferencesDelegate(preferences, HomeViewModel.USER_ID, "")

    private val _isLoadingCats = MutableLiveData(true)
    val isLoadingCats: LiveData<Boolean> = _isLoadingCats

    private val _errMessages = MutableLiveData("")
    val errMessages: LiveData<String> = _errMessages

    fun getCats(): Flow<List<Cat>> = flow {
        getFavoriteCatsUseCase(userId)
            .onStart { _isLoadingCats.postValue(true) }
            .collect { result ->
                result.onSuccess {
                    emit(it)
                }
                result.onFailure {
                    it.printStackTrace()
                    sendErrMessage("FavoritesViewModel -> ERR: ${it.localizedMessage}")
                }
                _isLoadingCats.postValue(false)
            }
    }

    private fun sendErrMessage(errText: String) {
        _errMessages.postValue(errText)
    }
}