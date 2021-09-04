package com.quantrics.yelp.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.quantrics.yelp.app.Yelp
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@Module
class NetworkModule @Inject constructor(var app: Yelp, var baseUrl:String)
{
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    fun provideRetrofit(gson: Gson?): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging);
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(httpClient!!.build())
            .build()
    }

    @Provides
    fun provideYelpService(retrofit: Retrofit): YelpService
    {
        return  retrofit.create(YelpService::class.java)
    }

    @Provides
    fun provideNetworkViewModel():NetworkViewModel
    {
        return NetworkViewModel(app)
    }

}