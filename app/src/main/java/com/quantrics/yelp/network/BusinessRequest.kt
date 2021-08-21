package com.quantrics.yelp.network

import retrofit2.http.Query

class BusinessRequest (var term:String)
{
    var location:String?    = "PH"
    var latitude:Double?    = null
    var longitude:Double?   = null
    var radius:Int?         = null
    var categories:String?  = null
    var locale:String?      = null
    var limit:Int?          = null
    var offset:Int?         = null
    var sortBy:String?      = null
    var price:String?       = null
    var openNow:Boolean?    = null
    var openAt:Int?         = null
    var arttributes:String? = null
}