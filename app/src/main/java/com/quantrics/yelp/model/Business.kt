package com.quantrics.yelp.model

import com.google.gson.annotations.SerializedName

class Business
{
    var id:String? = null
    var alias:String? = null
    var name:String? = null
    @SerializedName("image_url")
    var imageUrl:String? = null
    @SerializedName("is_closed")
    var isClosed:Boolean? = null
    var url:String? = null
    @SerializedName("review_count")
    var reviewCount:Int? = null
    var rating:Double? = null
    var phone:String? = null
    @SerializedName("display_phone")
    var displayPhone:String? = null
    var distance:Double? = null

    var coordinates:Coordinate? = null
    var location:Location? = null
    var categories = emptyArray<Category>()



}