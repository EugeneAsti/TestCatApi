package ru.aeyu.catapitestapp.ui.about

import android.app.Application
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.aeyu.catapitestapp.domain.models.Cat
import ru.aeyu.catapitestapp.domain.usecases.GetRemoteCatUseCase
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val getRemoteCatUseCase: GetRemoteCatUseCase,
    private val app: Application
) : AndroidViewModel(app) {
    private val _isLoadingCats = MutableLiveData(false)
    val isLoadingCats: LiveData<Boolean> = _isLoadingCats

    private val _errMessages = MutableLiveData("")
    val errMessages: LiveData<String> = _errMessages

    private val _infoMessages = MutableLiveData<String>()
    val infoMessages: LiveData<String> = _infoMessages

    private var _currentCat: Cat = Cat()

    var catImageId: String = ""


    fun getCatInformation(): Flow<Cat> = flow {
        val catImage = catImageId
        if (catImage.isNotEmpty())
            getRemoteCatUseCase(catImage)
                .flowOn(Dispatchers.IO)
                .onStart { _isLoadingCats.postValue(true) }
                .collect { result ->
                    result.onSuccess {
                        emit(it)
                        _currentCat = it
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

    fun onDownloadClick() {
        val fileName = "${_currentCat.id}.jpg"
        val fileToSave = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            saveFileOnAndroidLess29(fileToSave)
        else {
            saveFileOnAndroid29AndLater(fileToSave)
        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveFileOnAndroid29AndLater(fileToSave: File) {

        val newFileContentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileToSave.name)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

//        getApplication<Application>().contentResolver.insert(
//            Uri.parse(_currentCat.url),
//            newFileContentValues
//        )
        val resolver = app.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, newFileContentValues)
        if (uri != null) {
            viewModelScope.launch(Dispatchers.IO) {
                URL(_currentCat.url).openStream().use { input ->
                    resolver.openOutputStream(uri).use { output ->
                        input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                    }
                }
                _infoMessages.postValue("Файл скачан в папку Downloads")
            }
        }
    }

    private fun saveFileOnAndroidLess29(fileToSave: File) {
        viewModelScope.launch(Dispatchers.IO) {
            URL(_currentCat.url).openStream().use { input ->
                FileOutputStream(fileToSave).use { output ->
                    input.copyTo(output)
                }
            }
            _infoMessages.postValue("Файл скачан в папку Downloads")
        }
    }
}