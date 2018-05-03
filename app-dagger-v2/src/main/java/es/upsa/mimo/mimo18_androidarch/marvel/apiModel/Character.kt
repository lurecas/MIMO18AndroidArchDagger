package es.upsa.mimo.mimo18_androidarch.marvel.apiModel

import com.google.gson.annotations.SerializedName

data class Character(
        @SerializedName(value = "id") var id: String? = null,
        @SerializedName(value = "name") var name: String? = null,
        @SerializedName(value = "description") var description: String? = null,
        @SerializedName(value = "thumbnail") var thumbnail: Image? = null,
        @SerializedName(value = "resourceURI") var resourceURI: String? = null,
        @SerializedName(value = "modified") var modified: String? = null,
        @SerializedName(value = "urls") var urls: List<Urls>? = null,
        @SerializedName(value = "series") var series: Collection? = null,
        @SerializedName(value = "stories") var stories: Collection? = null,
        @SerializedName(value = "events") var events: Collection? = null,
        @SerializedName(value = "comics") var comics: Collection? = null
)