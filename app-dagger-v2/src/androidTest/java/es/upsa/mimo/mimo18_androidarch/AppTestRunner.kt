package es.upsa.mimo.mimo18_androidarch

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner


class AppTestRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(classLoader: ClassLoader, className: String, context: Context): Application {
        // replace Application class with mock one
        return super.newApplication(classLoader, MarvelTestApplication::class.java.name, context)
    }

}
