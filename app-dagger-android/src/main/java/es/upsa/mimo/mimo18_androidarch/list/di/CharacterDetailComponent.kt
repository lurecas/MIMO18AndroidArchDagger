package es.upsa.mimo.mimo18_androidarch.list.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import es.upsa.mimo.mimo18_androidarch.detail.CharacterDetailActivity
import es.upsa.mimo.mimo18_androidarch.di.module.ApiModule
import es.upsa.mimo.mimo18_androidarch.di.module.DataModule
import es.upsa.mimo.mimo18_androidarch.di.module.ImageModule
import es.upsa.mimo.mimo18_androidarch.di.module.NetworkModule
import es.upsa.mimo.mimo18_androidarch.di.scopes.DetailScope

@Subcomponent(
        modules = [
            NetworkModule::class,
            ApiModule::class,
            ImageModule::class,
            DataModule::class]
)
@DetailScope
interface CharacterDetailComponent : AndroidInjector<CharacterDetailActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CharacterDetailActivity>()
}