package es.upsa.mimo.mimo18_androidarch

import es.upsa.mimo.mimo18_androidarch.di.component.ApplicationComponent
import es.upsa.mimo.mimo18_androidarch.di.module.AndroidModule


class MarvelTestApplication : MarvelApplication() {

    override fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponentTest.builder()
                .androidModule(AndroidModule(this))
                .apiTestModule(ApiTestModule())
                .build()
    }

}