package com.quantrics.yelp.location

import android.content.Context
import android.location.Criteria
import android.location.LocationManager
import com.quantrics.yelp.app.Yelp
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class LocationModule @Inject constructor(var app:Yelp)
{
    @Provides
    fun provideLocationManager(): LocationManager
    {
        var locationManager =  app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager
    }

    @Provides
    fun provideProvider (locationManager: LocationManager):String
    {
        val criteria = Criteria()
        var provider:String? =  locationManager!!.getBestProvider(criteria, false)
        return provider!!
    }


}