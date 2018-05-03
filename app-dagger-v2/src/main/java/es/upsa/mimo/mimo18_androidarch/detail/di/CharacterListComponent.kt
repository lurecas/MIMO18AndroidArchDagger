package es.upsa.mimo.mimo18_androidarch.detail.di

import dagger.Component
import es.upsa.mimo.mimo18_androidarch.di.component.ApplicationComponent
import es.upsa.mimo.mimo18_androidarch.di.module.ApiModule
import es.upsa.mimo.mimo18_androidarch.di.module.DataModule
import es.upsa.mimo.mimo18_androidarch.di.module.ImageModule
import es.upsa.mimo.mimo18_androidarch.di.module.NetworkModule
import es.upsa.mimo.mimo18_androidarch.di.scopes.ListScope
import es.upsa.mimo.mimo18_androidarch.list.CharacterListActivity
import es.upsa.mimo.mimo18_androidarch.list.di.CharacterDetailComponent

@Component(
        dependencies = [ApplicationComponent::class],
        modules = [
            ApiModule::class,
            NetworkModule::class,
            ImageModule::class,
            DataModule::class]
)
@ListScope
interface CharacterListComponent {
    fun inject(activity: CharacterListActivity)

    fun plusDetailComponent(): CharacterDetailComponent

}