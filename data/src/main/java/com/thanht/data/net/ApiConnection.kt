package com.thanht.data.net

import com.thanht.data.BuildConfig
import com.thanht.data.cache.AppSharePref
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

private const val OK_HTTP_TIMEOUT = 15
private const val BASE_URL = "https://api.github.com/"

class ApiConnection @Inject constructor(private val mAppSharePref: AppSharePref) {
    val retrofit: Retrofit

    init {
        val isDebug = BuildConfig.DEBUG
        val okHttpClient =
            (if (isDebug) getUnsafeOkHttpClient() else OkHttpClient.Builder())
                .addNetworkInterceptor(makeLoggingInterceptor(isDebug))
                .addInterceptor(createChainInterceptor())
                .retryOnConnectionFailure(true)
                .followRedirects(true)
                .connectTimeout(OK_HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(OK_HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(OK_HTTP_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean) = HttpLoggingInterceptor()
        .apply {
            level = if (isDebug) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    private fun createChainInterceptor() = Interceptor { chain ->
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", mAppSharePref.token)
            .build()
        return@Interceptor chain.proceed(newRequest)
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder = try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                @SuppressWarnings("TrustAllX509TrustManager")
                override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                }

                @SuppressWarnings("TrustAllX509TrustManager")
                override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

            }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
            .apply { init(null, trustAllCerts, SecureRandom()) }

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}
