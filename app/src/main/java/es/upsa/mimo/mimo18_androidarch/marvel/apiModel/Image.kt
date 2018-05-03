package es.upsa.mimo.mimo18_androidarch.marvel.apiModel

import com.google.gson.annotations.SerializedName

data class Image(
        @SerializedName(value = "path") var path: String? = null,
        @SerializedName(value = "extension") var extension: String? = null
)
