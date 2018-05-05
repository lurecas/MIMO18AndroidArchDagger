package es.upsa.mimo.mimo18_androidarch.detail.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import es.upsa.mimo.mimo18_androidarch.di.module.ApiModule
import es.upsa.mimo.mimo18_androidarch.di.module.DataModule
import es.upsa.mimo.mimo18_androidarch.di.module.ImageModule
import es.upsa.mimo.mimo18_androidarch.di.module.NetworkModule
import es.upsa.mimo.mimo18_androidarch.di.scopes.ListScope
import es.upsa.mimo.mimo18_androidarch.list.CharacterListActivity

@Subcomponent(
        modules = [
            NetworkModule::class,
            ApiModule::class,
            ImageModule::class,
            DataModule::class]
)
@ListScope
interface CharacterListComponent : AndroidInjector<CharacterListActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CharacterListActivity>()
}