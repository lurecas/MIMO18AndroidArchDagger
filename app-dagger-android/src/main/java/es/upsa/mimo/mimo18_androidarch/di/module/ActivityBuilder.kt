package es.upsa.mimo.mimo18_androidarch.di.module

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import es.upsa.mimo.mimo18_androidarch.detail.CharacterDetailActivity
import es.upsa.mimo.mimo18_androidarch.detail.di.CharacterListComponent
import es.upsa.mimo.mimo18_androidarch.list.CharacterListActivity
import es.upsa.mimo.mimo18_androidarch.list.di.CharacterDetailComponent

@Module
abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(CharacterListActivity::class)
    abstract fun bindListActivity(
            builder: CharacterListComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(CharacterDetailActivity::class)
    abstract fun bindDetailActivity(
            builder: CharacterDetailComponent.Builder): AndroidInjector.Factory<out Activity>

}