package com.quantrics.yelp.model

import com.google.gson.annotations.SerializedName

class Location {

    var address1:String? = null
    var address2:String? = null
    var address3:String? = null
    var city:String? = null
    @SerializedName("zip_code")
    var zipCode:String? = null
    var country:String? = null
    var state:String? = null
    @SerializedName("display_address")
    var displayAddress = emptyArray<String>()
}