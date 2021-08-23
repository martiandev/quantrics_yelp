package com.quantrics.yelp.view.fragment

import android.content.Context
import android.location.LocationManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.quantrics.yelp.app.Yelp
import javax.inject.Inject

class MapFragment:SupportMapFragment()
{
    @Inject
    lateinit var locationManager: LocationManager
    @Inject
    lateinit var provider:String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as Yelp).appComponent.inject(this)

        getMapAsync{
            googleMap ->
            run {
                googleMap.setOnMapLoadedCallback {

                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                "14.384457200000002".toDouble(),
                                "120.8616776".toDouble()
                            ), 15f
                        )
                    )

                }
            }
        }
    }



}