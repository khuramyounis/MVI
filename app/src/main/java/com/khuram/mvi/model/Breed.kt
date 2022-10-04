package com.khuram.mvi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Breed(

    @Expose
    @SerializedName("id")
    var id: Int,

    @Expose
    @SerializedName("name")
    var name: String,

    @Expose
    @SerializedName("bred_for")
    var bredFor: String? = null,

    @Expose
    @SerializedName("breed_group")
    var breedGroup: String? = null,

    @Expose
    @SerializedName("life_span")
    var lifeSpan: String? = null,

    @Expose
    @SerializedName("temperament")
    var temperament: String? = null,

    @Expose
    @SerializedName("origin")
    var origin: String? = null,

    @Expose
    @SerializedName("image")
    var image: Image
)

data class Image(

    @Expose
    @SerializedName("id")
    var id: String,

    @Expose
    @SerializedName("width")
    var width: Int,

    @Expose
    @SerializedName("height")
    var height: Int,

    @Expose
    @SerializedName("url")
    var url: String
)