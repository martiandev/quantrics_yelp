package com.quantrics.yelp.view.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.model.Business
import com.quantrics.yelp.network.NetworkViewModel
import com.quantrics.yelp.preference.YelpPreference
import com.quantrics.yelp.view.adapter.ClickListener
import javax.inject.Inject

class MapFragment():SupportMapFragment(),
        LocationListener
{
    //======================================== Variable ============================================
    //injected
    @Inject
    lateinit var locationManager: LocationManager
    @Inject
    lateinit var provider:String
    @Inject
    lateinit var preference:YelpPreference
    //search results
    var businesses:List<Business>  = listOf()
    //GoogleMap
    var gmap:GoogleMap? = null
    lateinit var listener: ClickListener

    //==============================================================================================
    constructor(listener: ClickListener) : this()
    {
        this.listener = listener
    }

    //========================================= Lifecycle ==========================================
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as Yelp).appComponent.inject(this)
        updateCoordinates()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMapAsync{
            gmap = it
            gmap!!.setOnMapLoadedCallback {
                recenter()
            }
            gmap!!.setOnInfoWindowClickListener {
                if(listener!=null)
                {
                    for(b in businesses)
                    {
                        if(b.coordinates!!.latitude==it.position.latitude&&b.coordinates!!.longitude==it.position.longitude)
                        {
                            listener.onSelected(b)
                            break
                        }
                    }
                }
            }
        }

    }
    //==============================================================================================
    //======================================= Map Control ==========================================



    fun recenter()
    {
        if(gmap!=null)
        {
            gmap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    preference.getLat()!!,
                    preference.getLon()!!
                ), 10f
            ))
            gmap!!.addMarker(
                MarkerOptions()
                    .title("You are here")
                    .position(LatLng(preference.getLat()!!,preference.getLon()!!)))

        }

    }
    //==============================================================================================
    //========================================= Location ===========================================
    fun updateCoordinates()
    {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                303);
        }
        else
        {
            locationManager!!.requestLocationUpdates(provider!!, 0, 100f, this);
        }
        recenter()
    }
    //----------------------------------------- Location Listener ----------------------------------
    override fun onLocationChanged(p0: Location) {
        preference.setLat(p0.latitude)
        preference.setLon(p0.longitude)
        recenter()
    }
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //===================================== Search Result ==========================================
    fun updateBusinessses(businesses:List<Business>)
    {
        gmap!!.clear()
        recenter()

        this.businesses=businesses
        for(b in businesses)
        {
            gmap!!.addMarker(
                        MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .snippet(""+(Math.round(b!!.distance!!/1000*100)/100.0)+"km")
                            .title(b.name)
                            .position(LatLng(b.coordinates!!.latitude!!,b.coordinates!!.longitude!!)))

        }



    }

    fun findAPlace(name:String,distance:Double,lat:Double,lon:Double)
    {
        gmap!!.clear()
        recenter()
        gmap!!.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .snippet(""+(Math.round(distance!!/1000*100)/100.0)+"km")
                .title(name)
                .position(LatLng(lat,lon)))
    }
    //==============================================================================================










}