package com.powerise.demoapp.di

import com.powerise.testapplication.BuildConfig
import com.powerise.testapplication.home.di.HomeComponent
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Krunal
 */
@Module(subcomponents = arrayOf(HomeComponent::class))
class AppModule {

    @Provides
    fun provideLoggingIntercepter() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL).addConverterFactory(GsonConverterFactory.create()).client(
            client
        ).build()

}