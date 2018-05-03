package es.upsa.mimo.mimo18_androidarch.di.component

import dagger.Component
import es.upsa.mimo.mimo18_androidarch.detail.CharacterDetailActivity
import es.upsa.mimo.mimo18_androidarch.di.module.AndroidModule
import es.upsa.mimo.mimo18_androidarch.di.module.ApiModule
import es.upsa.mimo.mimo18_androidarch.di.module.DataModule
import es.upsa.mimo.mimo18_androidarch.di.module.ImageModule
import es.upsa.mimo.mimo18_androidarch.di.module.NetworkModule
import es.upsa.mimo.mimo18_androidarch.list.CharacterListActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidModule::class,
    ApiModule::class,
    NetworkModule::class,
    ImageModule::class,
    DataModule::class]
)
interface ApplicationComponent {

    fun inject(activity: CharacterDetailActivity)

    fun inject(activity: CharacterListActivity)

}