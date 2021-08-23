package com.quantrics.yelp.app

import android.app.Application
import com.quantrics.yelp.location.LocationModule
import com.quantrics.yelp.network.NetworkModule
import com.quantrics.yelp.preference.PreferenceModule

class Yelp : Application()
{
    open val  appComponent:AppComponent by lazy{
        DaggerAppComponent
            .builder()
            .networkModule(networkModule)
            .preferenceModule(preferenceModule)
            .locationModule(locationModule)
            .build()

    }

    open val networkModule by lazy{
        NetworkModule(this,"https://api.yelp.com/v3/")
    }


    open val preferenceModule by lazy{
        PreferenceModule(this)
    }

    open val locationModule by lazy{
        LocationModule(this)
    }

    override fun onCreate() {
        super.onCreate()

    }

}