package es.upsa.mimo.mimo18_androidarch

import dagger.Component
import es.upsa.mimo.mimo18_androidarch.di.component.ApplicationComponent
import es.upsa.mimo.mimo18_androidarch.di.module.AndroidModule
import es.upsa.mimo.mimo18_androidarch.list.CharacterListActivityTest
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidModule::class,
    ApiTestModule::class]
)
interface ApplicationComponentTest : ApplicationComponent {
    fun inject(test: CharacterListActivityTest)
}