package es.upsa.mimo.mimo18_androidarch

import android.app.Application
import es.upsa.mimo.mimo18_androidarch.di.component.ApplicationComponent
import es.upsa.mimo.mimo18_androidarch.di.component.DaggerApplicationComponent
import es.upsa.mimo.mimo18_androidarch.di.module.AndroidModule


open class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = createComponent()
    }

    open fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .build()
    }

    fun getComponent(): ApplicationComponent {
        return appComponent
    }

    companion object {
        @JvmStatic
        lateinit var appComponent: ApplicationComponent
    }


}
