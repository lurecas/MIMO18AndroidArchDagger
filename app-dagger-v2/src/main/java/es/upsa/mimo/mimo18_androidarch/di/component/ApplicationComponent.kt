package es.upsa.mimo.mimo18_androidarch.di.component

import android.content.Context
import android.content.res.Resources
import dagger.Component
import es.upsa.mimo.mimo18_androidarch.di.module.AndroidModule
import es.upsa.mimo.mimo18_androidarch.di.module.ApiModule
import es.upsa.mimo.mimo18_androidarch.di.module.NetworkModule
import es.upsa.mimo.mimo18_androidarch.marvel.MarvelApi
import es.upsa.mimo.mimo18_androidarch.util.ActivityNavigator
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidModule::class,
    NetworkModule::class,
    ApiModule::class]
)
interface ApplicationComponent {

    fun context(): Context
    fun resources(): Resources
    fun activityNavigator(): ActivityNavigator
    fun marvelApi(): MarvelApi

}