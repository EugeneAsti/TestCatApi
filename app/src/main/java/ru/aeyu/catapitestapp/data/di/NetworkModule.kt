package ru.aeyu.catapitestapp.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.aeyu.catapitestapp.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    fun provideInterceptor() : Interceptor {
        return if(BuildConfig.DEBUG)
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else
            noInterceptor()
    }


    @Provides
    fun provideOkHttpClient(
        interceptor: Interceptor,
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor {chain ->
                val request = chain.request()
                val modifiedRequest = with(request.newBuilder()) {
                    header("x-api-key", BuildConfig.API_KEY)
                    build()
                }
                chain.proceed(modifiedRequest)
            }
            .build()
    }

    private fun noInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(chain.request())
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = false
        }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.MAIN_URL)
            .client(okHttpClient)
            .build()
    }
}