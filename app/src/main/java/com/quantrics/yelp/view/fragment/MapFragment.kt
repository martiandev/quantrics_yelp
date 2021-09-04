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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.model.Business
import com.quantrics.yelp.network.NetworkViewModel
import com.quantrics.yelp.preference.YelpPreference
import javax.inject.Inject

class MapFragment:SupportMapFragment(),
        LocationListener
{
    var businesses:List<Business>  = listOf()

    @Inject
    lateinit var locationManager: LocationManager
    @Inject
    lateinit var provider:String
    @Inject
    lateinit var preference:YelpPreference

    private fun addMarkers(googleMap: GoogleMap,b:Business) {

            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(b.name)
                    .position(LatLng(b.coordinates!!.latitude!!,b.coordinates!!.longitude!!))
            )


    }

    fun updateBusinessses(businesses:List<Business>)
    {
        recenter()
        Log.i("GPS","ADDING MFrAGMNet:"+businesses.size)

        this.businesses=businesses
        getMapAsync{
                googleMap ->
            run {
                googleMap.setOnMapLoadedCallback {
                for(b in this.businesses)
                {
                    addMarkers(googleMap,b)
                }


                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as Yelp).appComponent.inject(this)
        updateCoordinates()

    }

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




    fun recenter()
    {
        getMapAsync{
                googleMap ->
            run {
                googleMap.setOnMapLoadedCallback {

                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                preference.getLat()!!,
                                preference.getLon()!!
                            ), 15f
                        )
                    )

                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onLocationChanged(p0: Location) {
        preference.setLat(p0.latitude)
        preference.setLon(p0.longitude)
        Log.i("YELP","COORDINATES:"+preference.getLat()+" : "+preference.getLon())
        recenter()
    }
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
}