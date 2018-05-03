package es.upsa.mimo.mimo18_androidarch.marvel


import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarvelApi {

    @GET("v1/public/characters")
    fun getCharacterList(
            @Query(value = "hash") hash: String,
            @Query(value = "ts") timestamp: Long,
            @Query(value = "apikey") apiKey: String
    ): Call<CharactersResponse>

    @GET("v1/public/characters/{character_id}")
    fun getCharacterDetail(
            @Path(value = "character_id") characterId: String,
            @Query(value = "hash") hash: String,
            @Query(value = "ts") timestamp: Long,
            @Query(value = "apikey") apiKey: String
    ): Call<CharactersResponse>

}