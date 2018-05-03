package es.upsa.mimo.mimo18_androidarch.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import es.upsa.mimo.mimo18_androidarch.R
import es.upsa.mimo.mimo18_androidarch.marvel.HashGenerator
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApi
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApiConstants
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.Character
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.CharactersResponse
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.character_detail_activity.*
import kotlinx.android.synthetic.main.view_recycler_view.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var characterID: String

    private val sectionAdapter: SectionedRecyclerViewAdapter = SectionedRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_detail_activity)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        characterID = intent.getStringExtra(BUNDLE_CHARACTER_ID)
        fetchMarvelCharacters(characterId = characterID)
    }

    private fun fetchMarvelCharacters(characterId: String) {

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
        marvelApi.getCharacterDetail(
                characterId = characterId,
                hash = HashGenerator.generate(
                        timestamp = timestamp,
                        privateKey = MarvelApiConstants.PRIVATE_API_KEY,
                        publicKey = MarvelApiConstants.PUBLIC_API_KEY)!!,
                timestamp = timestamp,
                apiKey = MarvelApiConstants.PUBLIC_API_KEY)
                .enqueue(object : Callback<CharactersResponse> {
                    override fun onResponse(call: Call<CharactersResponse>,
                                            response: Response<CharactersResponse>) {

                        if (response.isSuccessful) {
                            val characterList = response.body()!!.data!!.results!!.toList()
                            setupUI(characterList.first())

                        } else {
                            Snackbar.make(
                                    characterList,
                                    "Error obtaining character $characterId detail",
                                    Snackbar.LENGTH_LONG
                            ).show()
                        }

                    }

                    override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                        Log.e(TAG, "Error fetching cat images", t)
                    }
                })
    }


    private fun setupUI(character: Character) {

        val thumbnail = character.thumbnail

        Picasso.Builder(this)
                .indicatorsEnabled(false)
                .loggingEnabled(true)
                .build()
                .load(thumbnail?.path + "." + thumbnail?.extension)
                .fit()
                .into(characterImage)


        toolbar_layout?.title = character.name

        with(sectionAdapter) {


            val seriesNames = character.series?.items?.mapNotNull {
                it.name
            } ?: emptyList()

            val storiesNames = character.stories?.items?.mapNotNull {
                it.name
            } ?: emptyList()

            val comicsNames = character.comics?.items?.mapNotNull {
                it.name
            } ?: emptyList()


            addSection(
                    CharacterDetailSection(
                            this@CharacterDetailActivity.getString(R.string.character_detail_section_series),
                            seriesNames
                    )
            )

            addSection(
                    CharacterDetailSection(
                            this@CharacterDetailActivity.getString(R.string.character_detail_section_stories),
                            storiesNames
                    )
            )

            addSection(
                    CharacterDetailSection(
                            this@CharacterDetailActivity.getString(R.string.character_detail_section_comics),
                            comicsNames
                    )
            )

            characterList.adapter = this
        }

    }


    companion object {

        private val TAG = CharacterDetailActivity::class.java.canonicalName
        private const val BUNDLE_CHARACTER_ID = "char_id"

        fun launch(context: Context, charId: String) {
            with(Intent(context, CharacterDetailActivity::class.java)) {
                putExtra(BUNDLE_CHARACTER_ID, charId)
                context.startActivity(this)
            }
        }
    }

}
