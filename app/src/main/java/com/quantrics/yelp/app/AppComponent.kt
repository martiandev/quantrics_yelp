package com.quantrics.yelp.app

import com.quantrics.yelp.MainActivity
import com.quantrics.yelp.location.LocationModule
import com.quantrics.yelp.network.NetworkModule
import com.quantrics.yelp.network.NetworkViewModel
import com.quantrics.yelp.preference.PreferenceModule
import com.quantrics.yelp.view.fragment.MapFragment
import dagger.Component


@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    PreferenceModule::class,
    LocationModule::class
])
interface AppComponent {
    fun inject(mainActivity: MainActivity?)
    fun inject(networkViewModel: NetworkViewModel?)
    fun inject(mapFragment: MapFragment?)

}