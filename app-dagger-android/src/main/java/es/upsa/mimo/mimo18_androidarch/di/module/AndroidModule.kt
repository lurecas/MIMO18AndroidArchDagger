package es.upsa.mimo.mimo18_androidarch.di.module

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import es.upsa.mimo.mimo18_androidarch.MarvelApplication
import es.upsa.mimo.mimo18_androidarch.detail.di.CharacterListComponent
import es.upsa.mimo.mimo18_androidarch.list.di.CharacterDetailComponent
import es.upsa.mimo.mimo18_androidarch.util.ActivityNavigator
import es.upsa.mimo.mimo18_androidarch.util.ActivityNavigatorImpl


@Module(subcomponents = [
    CharacterListComponent::class,
    CharacterDetailComponent::class
])
class AndroidModule {

    @Provides
    fun provideContext(application: MarvelApplication): Context = application

    @Provides
    fun provideResources(application: MarvelApplication): Resources = application.resources

    @Provides
    fun provideNavigator(application: MarvelApplication): ActivityNavigator = ActivityNavigatorImpl(context = application)

}
