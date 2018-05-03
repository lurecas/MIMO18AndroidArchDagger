package es.upsa.mimo.mimo18_androidarch.marvel.apiModel

import com.google.gson.annotations.SerializedName

data class Collection(
        @SerializedName("items") var items: List<Items>? = null,
        @SerializedName("collectionURI") var collectionURI: String? = null,
        @SerializedName("available") var available: String? = null,
        @SerializedName("returned") var returned: String? = null
)
