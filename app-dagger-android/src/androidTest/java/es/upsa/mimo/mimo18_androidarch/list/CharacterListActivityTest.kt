package es.upsa.mimo.mimo18_androidarch.list

import android.app.Activity
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import es.upsa.mimo.mimo18_androidarch.MarvelApplication
import es.upsa.mimo.mimo18_androidarch.R
import es.upsa.mimo.mimo18_androidarch.espresso_utils.RecyclerViewItemCountAssertion
import es.upsa.mimo.mimo18_androidarch.espresso_utils.RecyclerViewMatcher
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApi
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.Character
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.CharacterDataContainer
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.CharactersResponse
import es.upsa.mimo.mimo18_androidarch.marvel.apiModel.Image
import es.upsa.mimo.mimo18_androidarch.util.ActivityNavigator
import es.upsa.mimo.mimo18_androidarch.util.HashGenerator
import es.upsa.mimo.mimo18_androidarch.util.ImageLoader
import es.upsa.mimo.mimo18_androidarch.util.TimestampProvider
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.any
import org.mockito.Matchers.anyLong
import org.mockito.Matchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Provider


@RunWith(AndroidJUnit4::class)
class CharacterListActivityTest {

    fun createFakeCharacterListActivityInjector(block: CharacterListActivity.() -> Unit)
            : DispatchingAndroidInjector<Activity> {

        val injector = AndroidInjector<Activity> { instance ->
            if (instance is CharacterListActivity) {
                instance.block()
            }
        }

        val factory = AndroidInjector.Factory<Activity> { injector }

        val map = mapOf(Pair<Class<out Activity>,
                Provider<AndroidInjector.Factory<out Activity>>>(CharacterListActivity::class.java, Provider { factory }))

        return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(map)
    }

    @get:Rule
    val activityTestRule = object : ActivityTestRule<CharacterListActivity>(
            CharacterListActivity::class.java,
            true,
            // false: do not launch the activity immediately
            false) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            val myApp = InstrumentationRegistry.getTargetContext().applicationContext as MarvelApplication
            myApp.dispatchingAndroidInjector = createFakeCharacterListActivityInjector {
                // Set up the stub we want to return in the mock
                mockCharacterResponse()
                api = mockApi()
                hashGenerator = mock(HashGenerator::class.java)
                `when`(hashGenerator.generate(
                        anyLong(),
                        anyString(),
                        anyString())).thenReturn("")
                timestampProvider = mock(TimestampProvider::class.java)
                imageLoader = mock(ImageLoader::class.java)
                navigator = mockedNavigator
            }
        }
    }

    var charactersResponse: CharactersResponse? = null
    var mockedNavigator: ActivityNavigator = mock(ActivityNavigator::class.java)

    private fun mockCharacterResponse() {
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

    private fun mockApi(): MarvelApi {

        val marvelApi = mock(MarvelApi::class.java)

        val mockedCall: Call<CharactersResponse> = mock(Call::class.java) as Call<CharactersResponse>

        `when`(marvelApi.getCharacterList(
                hash = anyString(),
                timestamp = anyLong(),
                apiKey = anyString())).thenReturn(mockedCall)

        `when`(marvelApi.getCharacterDetail(
                hash = anyString(),
                timestamp = anyLong(),
                apiKey = anyString(),
                characterId = anyString())).thenReturn(mockedCall)

        doAnswer(object : Answer<Any> {
            override fun answer(invocation: InvocationOnMock): Any? {
                val callback = invocation.getArgumentAt(0, Callback::class.java) as Callback<CharactersResponse>
                callback.onResponse(mockedCall, Response.success(charactersResponse))
                return null
            }
        }).`when`(mockedCall).enqueue(any(Callback::class.java) as Callback<CharactersResponse>?)

        return marvelApi

    }

    @Test
    fun characterTestIsAddedToTheScreen() {
        activityTestRule.launchActivity(Intent())

        onView(withId(R.id.characterList)).check(RecyclerViewItemCountAssertion(expectedCount = 1))

        onView(
                RecyclerViewMatcher(R.id.characterList).atPositionOnView(0, R.id.character_name)
        ).check(
                matches(withText(TEST_CHARACTER_NAME))
        )

    }

    @Test
    fun tapOnCharacterGoesToDetailScreen() {
        activityTestRule.launchActivity(Intent())

        Intents.init()

        onView(
                RecyclerViewMatcher(R.id.characterList).atPositionOnView(0, R.id.character_name)
        ).perform(ViewActions.click())

        verify(mockedNavigator).openCharacterActivity(anyString())

        Intents.release()

    }

    companion object {

        private val TEST_CHARACTER_NAME = "Test Name"
        private val TEST_CHARACTER_DESCRIPTION = "Test Description"
        private val TEST_CHARACTER_THUMBNAIL_PATH = "https://ep01.epimg.net/internacional/imagenes/2018/05/04/actualidad/1525434241_941769_1525434417_noticia_normal_recorte1"
        private val TEST_CHARACTER_THUMBNAIL_EXTENSION = "jpg"
    }

}