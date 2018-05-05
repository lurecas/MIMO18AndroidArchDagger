package es.upsa.mimo.mimo18_androidarch.util

interface TimestampProvider {
    fun getTimestamp(): Long
}

class TimestampProviderImpl : TimestampProvider {
    override fun getTimestamp(): Long = System.currentTimeMillis()
}

