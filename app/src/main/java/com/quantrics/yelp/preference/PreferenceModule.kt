package com.quantrics.yelp.preference

import android.content.Context
import com.quantrics.yelp.app.Yelp
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class PreferenceModule @Inject constructor(var app: Yelp)
{

    @Provides
    fun providePreference():YelpPreference
    {
        var yp:YelpPreference = YelpPreference()
        yp.sharedPreference = app.getSharedPreferences(YelpPreference.TAG_PREFERENCE, Context.MODE_PRIVATE)
        yp.editor = yp.sharedPreference.edit()
        return yp
    }
}