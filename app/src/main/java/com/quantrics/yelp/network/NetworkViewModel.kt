package com.quantrics.yelp.network

import android.content.Context
import android.util.Log
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
    protected val compositeDisposable = CompositeDisposable()

    constructor(yelp: Yelp)
    {
        yelp.appComponent.inject(this)
    }

    fun getAuth():String
    {
        return  "Bearer "+preference.getAuth()
    }

    fun search(request: BusinessRequest)
    {


    }


    fun search()
    {

        yelpService.searchBusiness(getAuth(),"",
            "PH",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
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
        if(response.body()!!.businesses.size>0)
        {
            var list = response.body()!!.businesses.toList()
            businesses.postValue(list)
        }
        else
        {
            businesses.postValue(listOf())
        }

    }
    fun onFail(call: Call<BusinessResponse>, t: Throwable)
    {
        businesses.postValue(listOf())
    }

}