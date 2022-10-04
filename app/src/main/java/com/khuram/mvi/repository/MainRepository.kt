package com.khuram.mvi.repository

import androidx.lifecycle.LiveData
import com.khuram.mvi.api.RetrofitBuilder
import com.khuram.mvi.model.Breed
import com.khuram.mvi.ui.main.state.MainViewState
import com.khuram.mvi.util.*


object MainRepository {

    fun getBreedList(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<Breed>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<Breed>>) {
                result.value = DataState.data(
                    data = MainViewState(
                        breeds = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<Breed>>> {
                return RetrofitBuilder.apiService.getBreeds()
            }
        }.asLiveData()
    }
}