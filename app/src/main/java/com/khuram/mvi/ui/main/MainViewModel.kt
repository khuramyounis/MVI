package com.khuram.mvi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.khuram.mvi.model.Breed
import com.khuram.mvi.repository.MainRepository
import com.khuram.mvi.ui.main.state.MainStateEvent
import com.khuram.mvi.ui.main.state.MainStateEvent.*
import com.khuram.mvi.ui.main.state.MainViewState
import com.khuram.mvi.util.AbsentLiveData
import com.khuram.mvi.util.DataState


class MainViewModel: ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    lateinit var selectedItem: Breed

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            handleStateEvent(stateEvent)
        }

    init {
        setStateEvent(GetBreedsEvent)
    }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when(stateEvent) {
            is GetBreedsEvent -> MainRepository.getBreedList()
            is None -> AbsentLiveData.create()
        }
    }

    fun setBreedListData(breeds: List<Breed>) {
        val update = getCurrentViewStateOrNew()
        update.breeds = breeds
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}