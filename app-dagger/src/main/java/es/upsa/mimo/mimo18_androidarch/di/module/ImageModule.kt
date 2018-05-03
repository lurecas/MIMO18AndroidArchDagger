package es.upsa.mimo.mimo18_androidarch.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import es.upsa.mimo.mimo18_androidarch.util.ImageLoader
import es.upsa.mimo.mimo18_androidarch.util.ImageLoaderImpl


@Module
class ImageModule {

    @Provides
    fun provideImageLoader(
            context: Context
    ): ImageLoader {
        return ImageLoaderImpl(
                context = context
        )
    }

}
