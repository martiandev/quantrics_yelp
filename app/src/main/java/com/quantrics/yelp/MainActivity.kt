package com.quantrics.yelp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.databinding.ActivityMainBinding
import com.quantrics.yelp.network.NetworkViewModel
import com.quantrics.yelp.preference.YelpPreference
import javax.inject.Inject
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.quantrics.yelp.view.fragment.SplashFragment
import com.quantrics.yelp.view.fragment.ViewPagerFragment


class MainActivity : AppCompatActivity() {


    var bottom_nav: BottomNavigationView? = null
    var menu:Menu? = null

    @Inject
    lateinit var  nvm:NetworkViewModel
    @Inject
    lateinit var preference:YelpPreference
    @Inject
    lateinit var provider:String

    var vpFragment:ViewPagerFragment? = null



    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        bottom_nav = findViewById(R.id.bottom_nav)
        bottom_nav!!.visibility = View.GONE
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            startSplash()
        }
        else
        {
            showViewPager()
            setBottomNavigation()
        }

    }



    fun startSplash()
    {
        binding.toolbar.visibility= View.GONE
        var splash:SplashFragment = SplashFragment()
        supportFragmentManager.beginTransaction().add(R.id.ll_main,splash,"SPLASH").commit()

    }
    fun showViewPager()
    {
        (application as Yelp).appComponent.inject(this)
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
        bottom_nav!!.visibility = View.VISIBLE
        vpFragment!!.bottom_nav = this.bottom_nav
        bottom_nav!!.setOnNavigationItemSelectedListener {
            vpFragment!!.selectItem(it.itemId)
            true
        }
        menu = bottom_nav!!.menu
        menu!!.add(Menu.NONE,0, Menu.NONE,getString(R.string.map))
            .setIcon(R.drawable.map)
        menu!!.add(Menu.NONE,1, Menu.NONE, getString(R.string.list))
            .setIcon(R.drawable.list)
        menu!!.add(Menu.NONE,2, Menu.NONE,getString(R.string.settings))
            .setIcon(R.drawable.setttings)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView: SearchView = menu.findItem(R.id.action_search).getActionView() as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                nvm.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


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
                    showViewPager()
                    setBottomNavigation()
                }
                else
                {
                    startSplash()
                }
            }
        }

    }

}