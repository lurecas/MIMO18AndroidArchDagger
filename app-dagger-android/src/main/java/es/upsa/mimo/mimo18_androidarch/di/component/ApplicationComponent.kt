package es.upsa.mimo.mimo18_androidarch.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import es.upsa.mimo.mimo18_androidarch.MarvelApplication
import es.upsa.mimo.mimo18_androidarch.di.module.ActivityBuilder
import es.upsa.mimo.mimo18_androidarch.di.module.AndroidModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidModule::class,
    ActivityBuilder::class,
    AndroidInjectionModule::class]
)
interface ApplicationComponent : AndroidInjector<MarvelApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: MarvelApplication): Builder

        fun build(): ApplicationComponent
    }

}