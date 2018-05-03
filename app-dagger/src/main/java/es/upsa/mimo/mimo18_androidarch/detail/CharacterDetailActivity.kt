package es.upsa.mimo.mimo18_androidarch.detail

import android.content.res.Resources
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import es.upsa.mimo.mimo18_androidarch.MarvelApplication
import es.upsa.mimo.mimo18_androidarch.R
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApi
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApiConstants
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.Character
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.CharactersResponse
import es.upsa.mimo.mimo18_androidarch.util.HashGenerator
import es.upsa.mimo.mimo18_androidarch.util.ImageLoader
import es.upsa.mimo.mimo18_androidarch.util.TimestampProvider
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.character_detail_activity.*
import kotlinx.android.synthetic.main.view_recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var characterID: String

    private val sectionAdapter: SectionedRecyclerViewAdapter = SectionedRecyclerViewAdapter()

    @Inject
    lateinit var api: MarvelApi

    @Inject
    lateinit var hashGenerator: HashGenerator

    @Inject
    lateinit var timestampProvider: TimestampProvider

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var androidResources: Resources


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_detail_activity)
        injectDependencies()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        characterID = intent.getStringExtra(BUNDLE_CHARACTER_ID)
        fetchMarvelCharacters(characterId = characterID)
    }

    private fun injectDependencies() {
        MarvelApplication.appComponent
                .inject(this)
    }

    private fun fetchMarvelCharacters(characterId: String) {
        val timestamp = timestampProvider.getTimestamp()

        api.getCharacterDetail(
                characterId = characterId,
                hash = hashGenerator.generate(
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


        imageLoader.loadImageFittedToImageView(
                imageUrl = thumbnail?.path + "." + thumbnail?.extension,
                imageView = characterImage
        )

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
                            androidResources.getString(R.string.character_detail_section_series),
                            seriesNames
                    )
            )

            addSection(
                    CharacterDetailSection(
                            androidResources.getString(R.string.character_detail_section_stories),
                            storiesNames
                    )
            )

            addSection(
                    CharacterDetailSection(
                            androidResources.getString(R.string.character_detail_section_comics),
                            comicsNames
                    )
            )

            characterList.adapter = this
        }

    }


    companion object {

        private val TAG = CharacterDetailActivity::class.java.canonicalName
        const val BUNDLE_CHARACTER_ID = "char_id"
    }

}
