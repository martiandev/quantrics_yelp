package com.quantrics.yelp.app

import android.app.Application
import com.quantrics.yelp.network.NetworkModule

class Yelp : Application()
{
    open val  appComponent:AppComponent by lazy{
        DaggerAppComponent
            .builder()
            .networkModule(networkModule)
            .build()

    }

    open val networkModule by lazy{
        NetworkModule(this,"https://api.yelp.com/v3/")
    }


    override fun onCreate() {
        super.onCreate()

    }

}