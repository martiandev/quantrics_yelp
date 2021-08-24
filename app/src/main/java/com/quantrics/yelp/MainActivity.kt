package com.quantrics.yelp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.databinding.ActivityMainBinding
import com.quantrics.yelp.network.NetworkViewModel
import com.quantrics.yelp.preference.YelpPreference
import javax.inject.Inject
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.quantrics.yelp.view.fragment.MapFragment
import com.quantrics.yelp.view.fragment.SplashFragment
import com.quantrics.yelp.view.fragment.ViewPagerFragment


class MainActivity : AppCompatActivity(),LocationListener {


    var bottom_nav: BottomNavigationView? = null
    var menu:Menu? = null
    @Inject
    lateinit var  nvm:NetworkViewModel
    @Inject
    lateinit var preference:YelpPreference

    var vpFragment:ViewPagerFragment? = null


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private fun addMarkers(googleMap: GoogleMap) {

            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title("Test")
                    .position(LatLng("14.384457200000002".toDouble(),"120.8616776".toDouble()))
            )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Yelp).appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        bottom_nav = findViewById(R.id.bottom_nav)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            startSplash()
        }
        else
        {
            showViewPager()
            setBottomNavigation()
        }


        nvm.businesses.observe(this, Observer {
            Toast.makeText(baseContext,"Total:"+it.size,Toast.LENGTH_LONG).show()
        })
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

    }



    fun startSplash()
    {
        binding.toolbar.visibility= View.GONE
        binding.fab.visibility = View.GONE
        var splash:SplashFragment = SplashFragment()
        supportFragmentManager.beginTransaction().add(R.id.ll_main,splash,"SPLASH").commit()

    }
    fun showViewPager()
    {
        vpFragment = ViewPagerFragment(bottom_nav!!)
        binding.toolbar.visibility= View.VISIBLE
        if(supportFragmentManager.findFragmentById(R.id.ll_main)!=null)
        {
            supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(R.id.ll_main)!!).commit()

        }
        supportFragmentManager.beginTransaction().add(R.id.ll_main, vpFragment!!,"ViewPager").commit()
    }




    fun setBottomNavigation()
    {


        vpFragment!!.bottom_nav = this.bottom_nav
//        bottom_nav!!.setOnNavigationItemSelectedListener (vpFragment!!)
        bottom_nav!!.setOnNavigationItemSelectedListener {
            vpFragment!!.selectItem(it.itemId)
            true
        }
        menu = bottom_nav!!.menu

        menu!!.add(Menu.NONE,0, Menu.NONE, getString(R.string.list))
            .setIcon(R.drawable.list)
        menu!!.add(Menu.NONE,1, Menu.NONE,getString(R.string.map))
            .setIcon(R.drawable.map)
        menu!!.add(Menu.NONE,2, Menu.NONE,getString(R.string.settings))
            .setIcon(R.drawable.setttings)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.i("GPS","ID:"+item.itemId)
        Log.i("GPS","ID:"+R.id.action_settings)
        return when (item.itemId) {
            R.id.action_settings -> {

                    vpFragment!!.selectItem(2)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
        return super.onSupportNavigateUp()
    }

    override fun onLocationChanged(p0: Location) {
        preference.setLat(p0.latitude)
        preference.setLon(p0.longitude)
        Log.i("GPS","LAT:"+preference.getLat())
        Log.i("GPS","LONG:"+preference.getLon())
    }


//    fun setUpGPS()
//    {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
//                303);
//        }
//        else
//        {
////            Log.i("GPS","REQUESTING")
////            locationManager!!.getLastKnownLocation(provider!!)
//
//        }
//    }

//    fun getCoordinates()
//    {
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
//                303);
//        }
//        else
//        {
////            Log.i("GPS","REQUESTING")
//////            locationManager!!.getLastKnownLocation(provider!!)
////
////            locationManager!!.requestLocationUpdates(provider!!, 0, 100f, this);
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode)
        {
            303 ->{
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    getCoordinates()
                    showViewPager()
                }
            }
        }

    }

    override fun onProviderEnabled(provider: String) {
        Log.i("GPS","Provider Enabled:"+provider)
    }

    override fun onProviderDisabled(provider: String) {
        Log.i("GPS","Provider Disabled:"+provider)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.i("GPS","Provider Status changed:"+provider)
    }
}