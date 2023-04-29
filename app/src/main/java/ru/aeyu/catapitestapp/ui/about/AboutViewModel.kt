package ru.aeyu.catapitestapp.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteCatUseCase
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val getRemoteCatUseCase: GetRemoteCatUseCase
) : ViewModel() {
    private val _isLoadingCats = MutableLiveData(false)
    val isLoadingCats: LiveData<Boolean> = _isLoadingCats

    private val _errMessages = MutableLiveData("")
    val errMessages: LiveData<String> = _errMessages

    private val _infoMessages = MutableLiveData<String>()
    val infoMessages: LiveData<String> = _infoMessages

    var catImageId: String? = ""

    fun getCatInformation(): Flow<Cat> = flow {
        val catImage = catImageId
        if (catImage != null)
            getRemoteCatUseCase(catImage)
                .flowOn(Dispatchers.IO)
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
}