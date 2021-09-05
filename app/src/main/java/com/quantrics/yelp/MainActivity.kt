package com.quantrics.yelp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.databinding.ActivityMainBinding
import com.quantrics.yelp.network.NetworkViewModel
import com.quantrics.yelp.preference.YelpPreference
import javax.inject.Inject
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.quantrics.yelp.model.Business
import com.quantrics.yelp.view.adapter.ClickListener
import com.quantrics.yelp.view.adapter.MapRequestListener
import com.quantrics.yelp.view.fragment.BusinessSelected
import com.quantrics.yelp.view.fragment.DetailFragment
import com.quantrics.yelp.view.fragment.SplashFragment
import com.quantrics.yelp.view.fragment.ViewPagerFragment


class MainActivity : AppCompatActivity(),ClickListener,MapRequestListener{


    var bottom_nav: BottomNavigationView? = null
    var menu:Menu? = null
    var view_divider:View ? = null
    var searchView: SearchView? = null
    var term:String? = null
    @Inject
    lateinit var  nvm:NetworkViewModel
    @Inject
    lateinit var preference:YelpPreference
    @Inject
    lateinit var provider:String

    var vpFragment:ViewPagerFragment? = null
    var detailFragment:DetailFragment? = null




    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        detailFragment = DetailFragment()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        bottom_nav = findViewById(R.id.bottom_nav)
        view_divider=findViewById(R.id.v_divider)
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
        nvm.businesses.observe(this, Observer {
            vpFragment!!.updateBusinesses(it)
        })

        vpFragment = ViewPagerFragment(bottom_nav!!,this)
        binding.toolbar.visibility= View.VISIBLE
        view_divider!!.visibility= View.VISIBLE
        bottom_nav!!.visibility= View.VISIBLE
        if(searchView!=null)
        {
            searchView!!.visibility =View.VISIBLE
        }

        binding.toolbar.title="Yelp"
        attachViewPager()
    }

    fun attachViewPager()
    {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false);

        if(supportFragmentManager.findFragmentById(R.id.ll_main)!=null)
        {
            supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(R.id.ll_main)!!).commit()

        }
        if(supportFragmentManager.findFragmentById(R.id.ll_detail)!=null)
        {
            supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(R.id.ll_detail)!!).commit()


        }
        supportFragmentManager.beginTransaction().add(R.id.ll_main, vpFragment!!,"ViewPager").commit()
    }


    override fun onBackPressed() {
        if(searchView!!.visibility==View.GONE)
        {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)

            binding.toolbar.visibility= View.VISIBLE
            binding.toolbar.title = "Yelp"
            view_divider!!.visibility= View.VISIBLE
            bottom_nav!!.visibility= View.VISIBLE
            if(searchView!=null)
            {
                searchView!!.visibility =View.VISIBLE
            }
            if(supportFragmentManager.findFragmentById(R.id.ll_detail)!=null)
            {
                supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(R.id.ll_detail)!!).commit()


            }
        }
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
        searchView = menu.findItem(R.id.action_search).getActionView() as SearchView
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                term = query
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
            android.R.id.home ->
            {
                onBackPressed()
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

    override fun onSelected(b: Business) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.title = b.name
        detailFragment!!.b=b
        if(supportFragmentManager.findFragmentById(R.id.ll_detail)!=null)
        {
            supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(R.id.ll_detail)!!).commit()


        }
        view_divider!!.visibility= View.GONE
        searchView!!.visibility = View.GONE
        bottom_nav!!.visibility = View.GONE
        supportFragmentManager.beginTransaction().add(R.id.ll_detail, detailFragment!!,"selection").commit()


    }

    override fun showOnMap(b: Business) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        binding.toolbar.visibility= View.VISIBLE
        binding.toolbar.title = "Yelp"
        view_divider!!.visibility= View.VISIBLE
        bottom_nav!!.visibility= View.VISIBLE
        if(searchView!=null)
        {
            searchView!!.visibility =View.VISIBLE
        }
        if(supportFragmentManager.findFragmentById(R.id.ll_detail)!=null)
        {
            supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(R.id.ll_detail)!!).commit()
        }
        vpFragment!!.viewPager!!.currentItem=0
        vpFragment!!.showOnMap(b)

    }

}