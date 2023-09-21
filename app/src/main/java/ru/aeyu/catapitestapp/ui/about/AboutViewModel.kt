package ru.aeyu.catapitestapp.ui.about

import android.app.Application
import android.os.Build
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteCatUseCase
import ru.aeyu.catapitestapp.ui.BaseViewModel
import ru.aeyu.catapitestapp.utils.SaveCatPicture
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val getRemoteCatUseCase: GetRemoteCatUseCase,
    private val app: Application
) : BaseViewModel() {

    private var _currentCat: Cat = Cat()

    var catImageId: String = ""

    fun getCatInformation(): Flow<Cat> = flow {
        val catImage = catImageId
        if (catImage.isNotEmpty())
            getRemoteCatUseCase(catImage)
                .flowOn(Dispatchers.IO)
                .onStart { setIsLoading(true) }
                .onCompletion {
                    it?.printStackTrace()
                    setIsLoading(false)
                }
                .collect { result ->
                    result.onSuccess {
                        emit(it)
                        _currentCat = it
                    }
                    result.onFailure {
                        it.printStackTrace()
                        sendErrMessage("HomeViewModel -> ERR: ${it.localizedMessage}")
                    }
                }
    }

    fun onDownloadClick() {
        val savePicture = SaveCatPicture(_currentCat.url, "${_currentCat.id}.jpg")
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                savePicture.saveFileOnAndroidLess29()
            else {
                savePicture.saveFileOnAndroid29AndLater(app.contentResolver)
            }
            if (result >= 0)
                sendInfoMessage("Файл сохранен в папку Downloads")
        }
    }
}