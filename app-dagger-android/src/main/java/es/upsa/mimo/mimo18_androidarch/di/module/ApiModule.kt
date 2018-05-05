package es.upsa.mimo.mimo18_androidarch.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApi
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApiConstants
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class ApiModule {

    @Provides
    fun provideMarvelApiService(
            retrofit: Retrofit
    ): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }

    @Provides
    fun provideRetrofit(
            converterFactory: Converter.Factory,
            okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(MarvelApiConstants.MARVEL_API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    @Provides
    fun provideGsonConverterFactory(
            gson: Gson
    ): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}
