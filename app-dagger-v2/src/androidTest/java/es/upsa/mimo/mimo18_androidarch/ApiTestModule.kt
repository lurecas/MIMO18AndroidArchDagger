package es.upsa.mimo.mimo18_androidarch

import dagger.Module
import dagger.Provides
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApi
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
class ApiTestModule {

    @Provides
    @Singleton
    fun provideMarvelApiService(): MarvelApi {
        return mock(MarvelApi::class.java)
    }

}
