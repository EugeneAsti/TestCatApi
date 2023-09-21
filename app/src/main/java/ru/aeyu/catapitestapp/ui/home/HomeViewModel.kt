package ru.aeyu.catapitestapp.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.data.remote.repositories.PreferencesDelegate
import ru.aeyu.catapitestapp.domain.models.Breed
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.domain.usecases.GetPagingCatsRemoteUseCase
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteBreedsUseCase
import ru.aeyu.catapitestapp.domain.usecases.SetFavoriteCatUseCase
import ru.aeyu.catapitestapp.ui.BaseViewModel
import ru.aeyu.catapitestapp.ui.extensions.bytesToHex
import ru.aeyu.catapitestapp.utils.USER_ID
import javax.crypto.KeyGenerator
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPagingCatsRemoteUseCase: GetPagingCatsRemoteUseCase,
    private val getRemoteBreedsUseCase: GetRemoteBreedsUseCase,
    private val setFavoriteCatUseCase: SetFavoriteCatUseCase,
    preferences: SharedPreferences
) : BaseViewModel() {

    private val _onBreedChanged = MutableLiveData("")
    val onBreedChanged: LiveData<String> = _onBreedChanged

    private var userId: String by PreferencesDelegate(preferences, USER_ID, "")

    private val _sumOfAddedFavorites = MutableSharedFlow<Int>()
    val sumOfAddedFavorites: SharedFlow<Int> = _sumOfAddedFavorites.asSharedFlow()

    private var addedFavorites = 0

    init {
        generateUserIdIfEmpty()
    }

    private fun generateUserIdIfEmpty() {
        if (userId.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val kg = KeyGenerator.getInstance("AES")
                val key = kg.generateKey()
                userId = key.encoded.bytesToHex()
            }
        }
    }

    fun onAddToFavoriteClick(cat: Cat?) {
        if (cat != null) {
            viewModelScope.launch(Dispatchers.IO) {
                setFavoriteCatUseCase(cat, userId).collect { result ->
                    result.onSuccess {
                        if (it)
                            _sumOfAddedFavorites.emit(++addedFavorites)
                    }
                    result.onFailure {
                        it.printStackTrace()
                        sendErrMessage("onAddToFavoriteClick -> ERR: ${it.localizedMessage}")
                    }
                }
            }
        }
    }

    fun getCatsPaging(selectedBreedId: String): Flow<PagingData<Cat>> = flow {
        getPagingCatsRemoteUseCase(viewModelScope, 12, selectedBreedId)
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
                    sendErrMessage("HomeViewModel -> ERR: ${it.localizedMessage}")
                }
            }
    }

    fun getBreeds(): Flow<List<Breed>> = flow {
        getRemoteBreedsUseCase()
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
                    sendErrMessage("HomeViewModel -> ERR: ${it.localizedMessage}")
                }
            }
    }

    fun setBreed(breed: Breed) {
        _onBreedChanged.value = breed.id
    }

    fun clearFavorites() {
        viewModelScope.launch {
            addedFavorites = 0
            _sumOfAddedFavorites.emit(addedFavorites)
        }
    }
}