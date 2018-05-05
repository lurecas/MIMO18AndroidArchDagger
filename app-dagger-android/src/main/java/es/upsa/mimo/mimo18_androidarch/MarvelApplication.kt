package es.upsa.mimo.mimo18_androidarch

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import es.upsa.mimo.mimo18_androidarch.di.component.DaggerApplicationComponent


open class MarvelApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
                .application(this)
                .build()
    }

}
