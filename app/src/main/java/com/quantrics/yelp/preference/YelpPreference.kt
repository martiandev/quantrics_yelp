package com.quantrics.yelp.preference

import android.content.SharedPreferences

class YelpPreference
{

    lateinit var sharedPreference:SharedPreferences
    lateinit var editor:SharedPreferences.Editor

    companion object
    {
        val TAG_PREFERENCE  = "YELP"
        val TAG_AUTH        = "Authorization"
        val TAG_LOCATION    = "Location"
        val TAG_RADIUS      = "Radius"
        val TAG_LIMIT       = "Limit"
        val TAG_OPEN        = "Open"
    }


    fun getAuth():String?{
        return  sharedPreference.getString(TAG_AUTH,"aakTWiUma-QmuYNfAcBDW4j2LxkRSCUjLEo7-ULt9KQ36MFzlGYL1q9oAdKekKvtdSlq79GwQZ3PAFBzsX99C5Qaf8tSLBoGhNpo9EIQwJJRnB7v3Obz6lmkTF8gYXYx")
    }
    fun setAuth(value:String)
    {
        editor.putString(TAG_AUTH,value).commit()
    }



    fun getLocation():String?{
        return  sharedPreference.getString(TAG_LOCATION,null)
    }
    fun setLocation(value:String)
    {
        editor.putString(TAG_LOCATION,value).commit()
    }
    fun getRadius():Int?{
        return  sharedPreference.getInt(TAG_RADIUS,0)
    }
    fun setRadius(value:Int?)
    {
        if(value==null)
        {
            editor.putInt(TAG_RADIUS,0).commit()
        }
        editor.putInt(TAG_RADIUS,value!!).commit()
    }
    fun getLimit():Int?{
        return  sharedPreference.getInt(TAG_LIMIT,0)
    }
    fun setLimit(value:Int?)
    {
        if(value==null)
        {
            editor.putInt(TAG_LIMIT,0).commit()
        }
        editor.putInt(TAG_LIMIT,value!!).commit()
    }
    fun getOpen():Boolean?{
        return  sharedPreference.getBoolean(TAG_OPEN,false)
    }
    fun setOpen(value:Boolean?)
    {
        editor.putBoolean(TAG_OPEN,value!!).commit()
    }


}