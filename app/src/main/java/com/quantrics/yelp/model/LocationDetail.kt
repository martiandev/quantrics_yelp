package com.quantrics.yelp.model

import com.google.gson.annotations.SerializedName

class LocationDetail {

    var address1:String? = null
    var address2:String? = null
    var address3:String? = null
    var city:String? = null
    @SerializedName("zip_code")
    var zipCode:String? = null
    var country:String? = null
    var state:String? = null
    @SerializedName("cross_streets")
    var cross:String? = null
    @SerializedName("display_address")
    var displayAddress = emptyArray<String>()


    fun getAddress():String
    {
        var address:String = ""
        for(s in displayAddress)
        {
            if(address.length>1)
            {
                address=address+", "
            }
            address=address+s
        }
        return address

    }
}