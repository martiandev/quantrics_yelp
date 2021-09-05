package com.quantrics.yelp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BusinessDetail
{
    var id:String? = null
    var alias:String? = null
    var name:String? = null
    @SerializedName("image_url")
    var imageUrl:String? = null
    @SerializedName("is_claimed")
    var isClaimed:Boolean? = null
    @SerializedName("is_closed")
    var isClosed:Boolean? = null
    var url:String? = null
    var phone:String? = null
    @SerializedName("display_phone")
    var displayPhone:String? = null
    @SerializedName("review_count")
    var reviewCount:Int? = null
    var categories = emptyArray<Category>()
    var rating:Double? = null


    var location:LocationDetail? = null
    var coordinates:Coordinate? = null
    var photos = emptyArray<String>()


    var hours = emptyArray<Hour>()
    var transactions = emptyArray<String>()


}