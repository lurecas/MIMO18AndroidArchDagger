package es.upsa.mimo.mimo18_androidarch.di.module

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import es.upsa.mimo.mimo18_androidarch.MarvelApplication
import es.upsa.mimo.mimo18_androidarch.util.ActivityNavigator
import es.upsa.mimo.mimo18_androidarch.util.ActivityNavigatorImpl


@Module
class AndroidModule(private val application: MarvelApplication) {

    @Provides
    fun provideContext(): Context = application.applicationContext

    @Provides
    fun provideResources(): Resources = application.resources

    @Provides
    fun provideNavigator(
            context: Context
    ): ActivityNavigator = ActivityNavigatorImpl(context = context)

}
