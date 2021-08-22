package com.quantrics.yelp.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

//rFO7MUtHGA4nwlMWEUWmLQ
//

interface YelpService
{

    @GET("businesses/search")
    fun searchBusiness(
        @Header("authorization") token:String?,
        @Query("term") term:String?,
        @Query("location") location:String?,
        @Query("latitude") latitude:Double?,
        @Query("longitude") longitude:Double?,
        @Query("radius") radius:Int?,
        @Query("categories") categories:String?,
        @Query("locale") locale:String?,
        @Query("limit") limit:Int?,
        @Query("offset") offset:Int?,
        @Query("sort_by") sortBy:String?,
        @Query("price") price:String?,
        @Query("open_now") openNow:Boolean?,
        @Query("open_at") openAt:Int?,
        @Query("attributes") arttributes:String?
    ): Call<BusinessResponse>


}