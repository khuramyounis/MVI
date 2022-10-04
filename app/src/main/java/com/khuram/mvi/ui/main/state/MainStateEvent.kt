package com.khuram.mvi.ui.main.state


sealed class MainStateEvent {

    object GetBreedsEvent : MainStateEvent()

    object None: MainStateEvent()
}