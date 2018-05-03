package es.upsa.mimo.mimo18_androidarch.list

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import com.google.gson.Gson
import es.upsa.mimo.mimo18_androidarch.R
import es.upsa.mimo.mimo18_androidarch.detail.CharacterDetailActivity
import es.upsa.mimo.mimo18_androidarch.marvel.HashGenerator
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApi
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApiConstants
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.CharactersResponse
import kotlinx.android.synthetic.main.character_list_activity.*
import kotlinx.android.synthetic.main.view_recycler_view.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_list_activity)

        setSupportActionBar(toolbar)

        val adapter = CharacterListAdapter(
                object : CharacterListAdapter.ImageOnClick {
                    override fun imageClicked(view: ImageView, url: String) {
                        CharacterDetailActivity.launch(this@CharacterListActivity, url)
                    }
                })
        characterList.adapter = adapter

        fetchMarvelCharacters(adapter)
    }

    private fun fetchMarvelCharacters(adapter: CharacterListAdapter) {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
                .baseUrl(MarvelApiConstants.MARVEL_API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()

        val timestamp = System.currentTimeMillis()

        val marvelApi = retrofit.create(MarvelApi::class.java)
        marvelApi.getCharacterList(
                hash = HashGenerator.generate(
                        timestamp = timestamp,
                        privateKey = MarvelApiConstants.PRIVATE_API_KEY,
                        publicKey = MarvelApiConstants.PUBLIC_API_KEY)!!,
                timestamp = timestamp,
                apiKey = MarvelApiConstants.PUBLIC_API_KEY
        ).enqueue(object : Callback<CharactersResponse> {
            override fun onResponse(call: Call<CharactersResponse>,
                                    response: Response<CharactersResponse>) {

                if (response.isSuccessful) {
                    val characterList = response.body()!!.data!!.results!!.toList()
                    adapter.characters(characterList)

                } else {
                    Snackbar.make(
                            characterList,
                            "Error obtaining characters",
                            Snackbar.LENGTH_LONG
                    ).show()
                }

            }

            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                Log.e(TAG, "Error fetching cat images", t)
            }
        })
    }

    companion object {

        private val TAG = CharacterDetailActivity::class.java.canonicalName
    }

}
