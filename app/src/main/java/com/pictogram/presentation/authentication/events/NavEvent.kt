package com.pictogram.presentation.authentication.events

sealed class NavEvent {
    object NavigateToRegister : NavEvent()
    object NavigateBack : NavEvent()

}