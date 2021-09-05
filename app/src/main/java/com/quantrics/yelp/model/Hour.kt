package com.quantrics.yelp.model

import com.google.gson.annotations.SerializedName

class Hour {
    @SerializedName("open")
    var hours = emptyArray<Open>()
    @SerializedName("hours_type")
    var hoursType:String? = null
    @SerializedName("is_open_now")
    var isOpenNow:Boolean? = null

}