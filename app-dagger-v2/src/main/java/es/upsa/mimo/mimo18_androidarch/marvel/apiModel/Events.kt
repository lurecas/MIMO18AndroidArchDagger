package es.upsa.mimo.mimo18_androidarch.marvel.apiModel

import com.google.gson.annotations.SerializedName

data class Events(
        @SerializedName(value = "items") var items: List<Items>? = null,
        @SerializedName(value = "collectionURI") var collectionURI: String? = null,
        @SerializedName(value = "available") var available: String? = null,
        @SerializedName(value = "returned") var returned: String? = null
)
