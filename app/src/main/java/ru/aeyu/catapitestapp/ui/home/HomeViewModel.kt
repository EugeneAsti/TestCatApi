package ru.aeyu.catapitestapp.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.data.remote.repositories.PreferencesDelegate
import ru.aeyu.catapitestapp.domain.cicerone.repository.home.HomeFragmentScreenRouter
import ru.aeyu.catapitestapp.domain.models.Breed
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.domain.usecases.GetPagingCatsRemoteUseCase
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteBreedsUseCase
import ru.aeyu.catapitestapp.domain.usecases.SetFavoriteCatUseCase
import ru.aeyu.catapitestapp.ui.extensions.bytesToHex
import javax.crypto.KeyGenerator
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPagingCatsRemoteUseCase: GetPagingCatsRemoteUseCase,
    private val getRemoteBreedsUseCase: GetRemoteBreedsUseCase,
    private val setFavoriteCatUseCase: SetFavoriteCatUseCase,
    private val homeFragmentScreenRouter: HomeFragmentScreenRouter,
    preferences: SharedPreferences
) : ViewModel() {

    companion object {
        const val USER_ID = "catsApiTestUserId"
    }

    //    private val _catsList = MutableSharedFlow<List<Cat>>()
//    val catsList: SharedFlow<List<Cat>> = _catsList.asSharedFlow()
    private val _isLoadingCats = MutableLiveData(true)
    val isLoadingCats: LiveData<Boolean> = _isLoadingCats

    private val _errMessages = MutableLiveData("")
    val errMessages: LiveData<String> = _errMessages

    private val _infoMessages = MutableLiveData<String>()
    val infoMessages: LiveData<String> = _infoMessages

//    var selectedBreed: String = ""

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

    private fun sendErrMessage(errText: String) {
        _errMessages.postValue(errText)
    }

    private fun sendInfoMessage(info: String) {
        _infoMessages.postValue(info)
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
        _isLoadingCats.value = true
        getPagingCatsRemoteUseCase(viewModelScope, 12, selectedBreedId)
            .collect { result ->
                result.onSuccess {
                    emit(it)
                }
                result.onFailure {
                    it.printStackTrace()
                    sendErrMessage("HomeViewModel -> ERR: ${it.localizedMessage}")
                }
                _isLoadingCats.postValue(false)
            }
    }

    fun getBreeds(): Flow<List<Breed>> = flow {
        getRemoteBreedsUseCase()
            .onStart { _isLoadingCats.postValue(true) }
            .collect { result ->
                result.onSuccess {
                    emit(it)
                }
                result.onFailure {
                    it.printStackTrace()
                    sendErrMessage("HomeViewModel -> ERR: ${it.localizedMessage}")
                }
                _isLoadingCats.postValue(false)
            }
    }

    fun setBreed(breed: Breed) {
        _onBreedChanged.value = breed.id
    }

    fun clearFavorites(){
        viewModelScope.launch{
            addedFavorites = 0
         _sumOfAddedFavorites.emit(addedFavorites)
        }
    }

    fun onCatClicked(catId: String){
        homeFragmentScreenRouter.onCatClicked(catId)
    }
}