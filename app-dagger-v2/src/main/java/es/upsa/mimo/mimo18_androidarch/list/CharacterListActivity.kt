package es.upsa.mimo.mimo18_androidarch.list

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import es.upsa.mimo.mimo18_androidarch.MarvelApplication
import es.upsa.mimo.mimo18_androidarch.R
import es.upsa.mimo.mimo18_androidarch.detail.CharacterDetailActivity
import es.upsa.mimo.mimo18_androidarch.detail.di.DaggerCharacterListComponent
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApi
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApiConstants
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.CharactersResponse
import es.upsa.mimo.mimo18_androidarch.util.ActivityNavigator
import es.upsa.mimo.mimo18_androidarch.util.HashGenerator
import es.upsa.mimo.mimo18_androidarch.util.ImageLoader
import es.upsa.mimo.mimo18_androidarch.util.TimestampProvider
import kotlinx.android.synthetic.main.character_list_activity.*
import kotlinx.android.synthetic.main.view_recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CharacterListActivity : AppCompatActivity() {

    @Inject
    lateinit var api: MarvelApi

    @Inject
    lateinit var hashGenerator: HashGenerator

    @Inject
    lateinit var timestampProvider: TimestampProvider

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var navigator: ActivityNavigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_list_activity)

        setSupportActionBar(toolbar)

        injectDependencies()

        val adapter = CharacterListAdapter(
                imageLoader = imageLoader,
                itemListener = object : CharacterListAdapter.OnItemClickListener {

                    override fun onItemClicked(charId: String) {
                        navigator.openCharacterActivity(charId = charId)
                    }

                })
        characterList.adapter = adapter

        fetchMarvelCharacters(adapter)
    }

    private fun injectDependencies() {
        DaggerCharacterListComponent.builder()
                .applicationComponent(MarvelApplication.appComponent)
                .build()
                .inject(this)
    }


    private fun fetchMarvelCharacters(adapter: CharacterListAdapter) {

        val timestamp = timestampProvider.getTimestamp()

        api.getCharacterList(
                hash = hashGenerator.generate(
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
