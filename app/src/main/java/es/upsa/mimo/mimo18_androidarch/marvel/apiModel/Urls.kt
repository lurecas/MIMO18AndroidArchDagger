package es.upsa.mimo.mimo18_androidarch.marvel.apiModel

import com.google.gson.annotations.SerializedName

data class Urls(
        @SerializedName(value = "type") var type: String? = null,
        @SerializedName(value = "url") var url: String? = null
)