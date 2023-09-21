package ru.aeyu.catapitestapp.ui.actions

sealed class UiActions {
    data class OnLoading(val isLoading: Boolean) : UiActions()
    data class OnError(val message: String): UiActions()
    data class OnInformation(val message: String): UiActions()
}