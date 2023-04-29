package ru.aeyu.catapitestapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.domain.models.Breed
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.domain.usecases.GetPagingCatsRemoteUseCase
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteBreedsUseCase
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteCatsUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRemoteCatsUseCase: GetRemoteCatsUseCase,
    private val getPagingCatsRemoteUseCase: GetPagingCatsRemoteUseCase,
    private val getRemoteBreedsUseCase: GetRemoteBreedsUseCase,

    ) : ViewModel() {

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



    fun getCats(selectedBreedId: String): Flow<List<Cat>> = flow {
        getRemoteCatsUseCase(10, selectedBreedId)
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

    private fun sendErrMessage(errText: String) {
        _errMessages.postValue(errText)
    }

    private fun sendInfoMessage(info: String) {
        _infoMessages.postValue(info)
    }


    fun onAddToFavoriteClick(cat: Cat?) {

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

}