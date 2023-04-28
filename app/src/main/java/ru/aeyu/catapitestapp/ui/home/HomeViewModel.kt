package ru.aeyu.catapitestapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.domain.usecases.GetPagingCatsRemoteUseCase
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteCatsUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRemoteCatsUseCase: GetRemoteCatsUseCase,
    private val getPagingCatsRemoteUseCase: GetPagingCatsRemoteUseCase
): ViewModel() {

//    private val _catsList = MutableSharedFlow<List<Cat>>()
//    val catsList: SharedFlow<List<Cat>> = _catsList.asSharedFlow()

    private val _isLoadingOutlets = MutableLiveData(false)
    val isLoadingOutlets: LiveData<Boolean> = _isLoadingOutlets

    private val _errMessages = MutableLiveData("")
    val errMessages: LiveData<String> = _errMessages

    private val _infoMessages = MutableLiveData<String>()
    val infoMessages: LiveData<String> = _infoMessages

    private val selectedBreed: String = ""

    fun getCats(): Flow<List<Cat>> = flow {
        getRemoteCatsUseCase(10, selectedBreed)
            .onStart { _isLoadingOutlets.postValue(true) }
            .collect { result ->
                result.onSuccess {
                    emit(it)
                }
                result.onFailure {
                    it.printStackTrace()
                    sendErrMessage("HomeViewModel -> ERR: ${it.localizedMessage}")
                }
                _isLoadingOutlets.postValue(false)
            }
    }

    private fun sendErrMessage(errText: String) {
        _errMessages.postValue(errText)
    }

    private fun sendInfoMessage(info: String) {
        _infoMessages.postValue(info)
    }

    fun onCatClicked(cat: Cat?){

    }

    fun getCatsPaging(): Flow<PagingData<Cat>> =
        getPagingCatsRemoteUseCase(viewModelScope, 15, selectedBreed)


}