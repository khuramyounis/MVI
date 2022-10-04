package com.khuram.mvi.ui.main.state

import com.khuram.mvi.model.Breed


data class MainViewState(

    var breeds: List<Breed>? = null
)
