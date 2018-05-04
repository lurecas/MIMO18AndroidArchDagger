package es.upsa.mimo.mimo18_androidarch.list

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import es.upsa.mimo.mimo18_androidarch.DaggerApplicationComponentTest
import es.upsa.mimo.mimo18_androidarch.MarvelTestApplication
import es.upsa.mimo.mimo18_androidarch.R
import es.upsa.mimo.mimo18_androidarch.detail.CharacterDetailActivity
import es.upsa.mimo.mimo18_androidarch.espresso_utils.RecyclerViewItemCountAssertion
import es.upsa.mimo.mimo18_androidarch.espresso_utils.RecyclerViewMatcher
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApi
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.Character
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.CharacterDataContainer
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.CharactersResponse
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.Image
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers
import org.mockito.Matchers.any
import org.mockito.Matchers.anyLong
import org.mockito.Matchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class CharacterListActivityTest {

    @get:Rule
    var charActivityRule = ActivityTestRule(
            CharacterListActivity::class.java,
            true,
            // false: do not launch the activity immediately
            false)

    @Inject
    lateinit var api: MarvelApi

    var charactersResponse: CharactersResponse? = null

    companion object {

        private val TEST_CHARACTER_NAME = "Test Name"
        private val TEST_CHARACTER_DESCRIPTION = "Test Description"
        private val TEST_CHARACTER_THUMBNAIL_PATH = "https://ep01.epimg.net/internacional/imagenes/2018/05/04/actualidad/1525434241_941769_1525434417_noticia_normal_recorte1"
        private val TEST_CHARACTER_THUMBNAIL_EXTENSION = "jpg"
    }

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        var app = instrumentation.targetContext.applicationContext as MarvelTestApplication
        val component = app.getComponent() as DaggerApplicationComponentTest
        component.inject(this)

        mockApi(api = api)

        // Set up the stub we want to return in the mock
        charactersResponse = Character(
                id = "1",
                name = TEST_CHARACTER_NAME,
                description = TEST_CHARACTER_DESCRIPTION,
                thumbnail = Image(path = TEST_CHARACTER_THUMBNAIL_PATH, extension = TEST_CHARACTER_THUMBNAIL_EXTENSION)
        ).let { character ->
            CharacterDataContainer(
                    count = 1,
                    results = arrayOf(character)
            )
        }.let { dataContainer ->
            CharactersResponse(
                    code = 200,
                    data = dataContainer
            )
        }

    }

    @After
    fun tearDown() {
        Mockito.reset(api)
    }

    private fun mockApi(api: MarvelApi) {

        val mockedCall: Call<CharactersResponse> = Mockito.mock(Call::class.java) as Call<CharactersResponse>

        `when`(api.getCharacterList(
                hash = anyString(),
                timestamp = anyLong(),
                apiKey = anyString())).thenReturn(mockedCall)

        `when`(api.getCharacterDetail(
                hash = anyString(),
                timestamp = anyLong(),
                apiKey = anyString(),
                characterId = anyString())).thenReturn(mockedCall)

        Mockito.doAnswer(object : Answer<Any> {
            override fun answer(invocation: InvocationOnMock): Any? {
                val callback = invocation.getArgumentAt(0, Callback::class.java) as Callback<CharactersResponse>
                callback.onResponse(mockedCall, Response.success(charactersResponse))
                return null
            }
        }).`when`(mockedCall).enqueue(any(Callback::class.java) as Callback<CharactersResponse>?)

    }

    @Test
    fun characterTestIsAddedToTheScreen() {
        charActivityRule.launchActivity(Intent())

        onView(withId(R.id.characterList)).check(RecyclerViewItemCountAssertion(expectedCount = 1))

        onView(
                RecyclerViewMatcher(R.id.characterList).atPositionOnView(0, R.id.character_name)
        ).check(
                matches(withText(TEST_CHARACTER_NAME))
        )

    }

    @Test
    fun tapOnCharacterGoesToDetailScreen() {
        charActivityRule.launchActivity(Intent())

        Intents.init()

        onView(
                RecyclerViewMatcher(R.id.characterList).atPositionOnView(0, R.id.character_name)
        ).perform(ViewActions.click())

        intended(hasComponent(CharacterDetailActivity::class.java.name))

        Intents.release()

    }


}