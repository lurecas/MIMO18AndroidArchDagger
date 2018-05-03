package es.upsa.mimo.mimo18_androidarch.marvel.apiModel

import com.google.gson.annotations.SerializedName


data class CharacterDataContainer(
        @SerializedName(value = "total") var total: Int = 0,
        @SerializedName(value = "limit") var limit: Int = 0,
        @SerializedName(value = "results") var results: Array<Character>? = null,
        @SerializedName(value = "count") var count: Int = 0,
        @SerializedName(value = "offset") var offset: Int = 0
)




