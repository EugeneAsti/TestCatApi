package ru.aeyu.catapitestapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.aeyu.catapitestapp.ui.actions.UiActions

abstract class BaseViewModel : ViewModel() {

    private val _uiActions = MutableSharedFlow<UiActions>()
    val uiActions: SharedFlow<UiActions> = _uiActions.asSharedFlow()

    protected suspend fun sendErrMessage(errText: String) {
        _uiActions.emit(UiActions.OnError(errText))
    }

    protected suspend fun sendInfoMessage(info: String) {
        _uiActions.emit(UiActions.OnInformation(info))
    }

    protected suspend fun setIsLoading(isLoading: Boolean){
        _uiActions.emit(UiActions.OnLoading(isLoading))
    }
}