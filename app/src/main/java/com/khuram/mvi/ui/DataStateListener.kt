package com.khuram.mvi.ui

import com.khuram.mvi.util.DataState


interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}