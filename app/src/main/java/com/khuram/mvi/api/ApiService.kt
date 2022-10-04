package com.khuram.mvi.api

import androidx.lifecycle.LiveData
import com.khuram.mvi.model.Breed
import com.khuram.mvi.util.GenericApiResponse
import retrofit2.http.*


interface ApiService {

    @GET("breeds")
    fun getBreeds(): LiveData<GenericApiResponse<List<Breed>>>
}