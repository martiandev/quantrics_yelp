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
        val TAG_LONG        = "Longitude"
        val TAG_LAT         = "Latitude"
        val TAG_RADIUS      = "Radius"
        val TAG_OPEN        = "Open"
        val TAG_DISTANCE    = "Distance"
        val TAG_RATING      = "Rating"
        val TAG_CAMERA      = "Camera"
    }


    fun getAuth():String?{
        return  sharedPreference.getString(TAG_AUTH,"aakTWiUma-QmuYNfAcBDW4j2LxkRSCUjLEo7-ULt9KQ36MFzlGYL1q9oAdKekKvtdSlq79GwQZ3PAFBzsX99C5Qaf8tSLBoGhNpo9EIQwJJRnB7v3Obz6lmkTF8gYXYx")
    }
    fun setAuth(value:String)
    {
        editor.putString(TAG_AUTH,value).commit()
    }

    fun getCamera():Float
    {
        return sharedPreference.getFloat(TAG_CAMERA,15f)
    }
    fun setCamera(value:Float?)
    {
        if(value==null)
        {
            editor.putFloat(TAG_CAMERA,15f).commit()
        }
        editor.putFloat(TAG_CAMERA,value!!).commit()
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
    fun getOpen():Boolean?{
        return  sharedPreference.getBoolean(TAG_OPEN,false)
    }
    fun setOpen(value:Boolean?)
    {
        editor.putBoolean(TAG_OPEN,value!!).commit()
    }
    fun getDistance():Boolean?{
        return  sharedPreference.getBoolean(TAG_DISTANCE,false)
    }
    fun setDistance(value:Boolean?)
    {
        editor.putBoolean(TAG_DISTANCE,value!!).commit()
    }

    fun getRating():Boolean?{
        return  sharedPreference.getBoolean(TAG_RATING,false)
    }
    fun setRating(value:Boolean?)
    {
        editor.putBoolean(TAG_RATING,value!!).commit()
    }

    fun getLat():Double?{
        return  sharedPreference.getString(TAG_LAT,"0.0")!!.toDouble()
    }
    fun setLat(value:Double?)
    {
        editor.putString(TAG_LAT,""+value!!).commit()
    }
    fun getLon():Double?{
        return  sharedPreference.getString(TAG_LONG,"0.0")!!.toDouble()
    }
    fun setLon(value:Double?)
    {
        editor.putString(TAG_LONG,""+value!!).commit()
    }

}