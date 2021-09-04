package com.quantrics.yelp.network

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.model.Business
import com.quantrics.yelp.preference.YelpPreference
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NetworkViewModel: ViewModel
{
    @Inject
    lateinit var yelpService: YelpService
    @Inject
    lateinit var preference: YelpPreference
    var businesses = MutableLiveData<List<Business>>()
    @Inject
    lateinit var locationManager: LocationManager
    @Inject
    lateinit var provider:String

    var term:String  = ""

    protected val compositeDisposable = CompositeDisposable()

    constructor(yelp: Yelp)
    {
        yelp.appComponent.inject(this)
    }

    fun getAuth():String
    {
        return  "Bearer "+preference.getAuth()
    }

    fun search(term: String)
    {
        this.term = term
        var radius:Int? = preference.getRadius()
        if(radius!!<1)
        {
            radius=null
        }
        else
        {
            radius = radius*1000
        }
        var isOpen:Boolean? = preference.getOpen()
        if(!isOpen!!)
        {
            isOpen==null
        }
        yelpService.searchBusiness(getAuth(),
            this.term,
            null,
            preference.getLat(),
            preference.getLon(),
            radius,
            null,
            null,
            30,
            null,
            null,
            null,
            preference.getOpen(),
            null,
            null
        ).enqueue(object : Callback<BusinessResponse> {
            override fun onResponse(
                call: Call<BusinessResponse>,
                response: Response<BusinessResponse>
            ) {
                onResponse(response)
            }
            override fun onFailure(call: Call<BusinessResponse>, t: Throwable) { onFail(call,t) }
        })
    }



    fun onResponse( response: Response<BusinessResponse>)
    {
        Log.i("GPS","SUCCESSS API:"+response.body()!!.businesses.size)
        if(response.body()!!.businesses.size>0)
        {
            var list = response.body()!!.businesses.toList()
            var result = listOf<Business>()
            if(preference.getDistance()!!)
            {

                result = list.sortedBy { it.distance }

            }
            else if (preference.getRating()!!)
            {
                result = list.sortedBy { it.rating }.reversed()
                for(b in result)
                {
                    Log.i("YELP",b.name+" : "+b.rating)
                }
            }
            else
            {
                result = list

            }

            businesses.postValue(result)
        }
        else
        {
            businesses.postValue(listOf())
        }

    }
    fun onFail(call: Call<BusinessResponse>, t: Throwable)
    {
        Log.i("GPS","FAO: API:"+t.localizedMessage)

        businesses.postValue(listOf())
    }




}