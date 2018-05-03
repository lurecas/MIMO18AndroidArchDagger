package es.upsa.mimo.mimo18_androidarch.di.module

import dagger.Module
import dagger.Provides
import es.upsa.mimo.mimo18_androidarch.util.HashGenerator
import es.upsa.mimo.mimo18_androidarch.util.HashGeneratorImpl
import es.upsa.mimo.mimo18_androidarch.util.TimestampProvider
import es.upsa.mimo.mimo18_androidarch.util.TimestampProviderImpl

@Module
class DataModule {

    @Provides
    fun provideHashGenerator(): HashGenerator = HashGeneratorImpl()

    @Provides
    fun provideTimestampProvider(): TimestampProvider = TimestampProviderImpl()

}
