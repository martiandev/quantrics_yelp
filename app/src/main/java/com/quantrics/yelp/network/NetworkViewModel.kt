package com.quantrics.yelp.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.model.Business
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NetworkViewModel: ViewModel
{
    @Inject
    lateinit var yelpService: YelpService
    var businesses = MutableLiveData<List<Business>>()
    protected val compositeDisposable = CompositeDisposable()

    constructor(yelp: Yelp)
    {
        yelp.appComponent.inject(this)
    }

    fun search()
    {

        yelpService.searchBusiness("Bearer aakTWiUma-QmuYNfAcBDW4j2LxkRSCUjLEo7-ULt9KQ36MFzlGYL1q9oAdKekKvtdSlq79GwQZ3PAFBzsX99C5Qaf8tSLBoGhNpo9EIQwJJRnB7v3Obz6lmkTF8gYXYx","test",
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
                Log.i("SUCCESS:",""+response.body()!!.businesses.size);
            }

            override fun onFailure(call: Call<BusinessResponse>, t: Throwable) {
                Log.i("FAILED:",t.localizedMessage);
            }

        })

    }

}