package com.khuram.mvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.khuram.mvi.util.*
import com.khuram.mvi.util.Constants.Companion.TESTING_NETWORK_DELAY
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main


@OptIn(DelicateCoroutinesApi::class)
abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)

        GlobalScope.launch(IO) {
            delay(TESTING_NETWORK_DELAY)

            withContext(Main) {
                val apiResponse = createCall()

                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)
                    handleNetworkCall(response)
                }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when(response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse -> {
                onReturnError(response.errorMessage)
            }
            is ApiEmptyResponse -> {
                onReturnError("HTTP 204. Returned Nothing!")
            }
        }
    }

    private fun onReturnError(message: String) {
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}