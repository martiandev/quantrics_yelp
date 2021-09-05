package com.quantrics.yelp.model

import com.google.gson.annotations.SerializedName

class Open {
    @SerializedName("is_overnight")
    var isOvernight:Boolean? = null
    var start:String? = null
    var end:String? = null
    var day:Int? = null

}