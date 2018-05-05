package es.upsa.mimo.mimo18_androidarch.marvel.apiModel

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
        @SerializedName(value = "code") var code: Int = 0,
        @SerializedName(value = "status") var status: String? = null,
        @SerializedName(value = "attributionText") var attributionText: String? = null,
        @SerializedName(value = "etag") private var eTag: String? = null,
        @SerializedName(value = "copyright") var copyright: String? = null,
        @SerializedName(value = "attributionHTML") var attributionHTML: String? = null,
        @SerializedName(value = "data") var data: CharacterDataContainer? = null
)