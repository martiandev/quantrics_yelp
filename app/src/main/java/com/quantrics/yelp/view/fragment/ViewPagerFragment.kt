package com.quantrics.yelp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.quantrics.yelp.R
import com.quantrics.yelp.model.Business
import com.quantrics.yelp.network.NetworkViewModel
import com.quantrics.yelp.view.adapter.ClickListener
import com.quantrics.yelp.view.adapter.HomePagerAdapter
import javax.inject.Inject

class ViewPagerFragment():Fragment()
{
    @Inject
    lateinit var  nvm: NetworkViewModel
    lateinit var cl: ClickListener
    var viewPager: ViewPager2? = null
    var adapter: HomePagerAdapter? = null

    var bottom_nav: BottomNavigationView? = null

    var search:SearchResultFragment? = null;
    var mapFragment:MapFragment? = null


    constructor(bottom_nav: BottomNavigationView,cl:ClickListener):this()
    {
        this.cl = cl
        this.bottom_nav = bottom_nav
    }



    fun updateBusinesses(businesses:List<Business>)
    {
        Log.i("GPS","ADDING VP:"+businesses.size)
        mapFragment!!.updateBusinessses(businesses)
        search!!.updateBusinesses(businesses)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.vp_main)
        this.viewPager!!.isUserInputEnabled = false
        setFragments()
    }

    fun setFragments() {
        var list: ArrayList<Fragment> = ArrayList()
        search = SearchResultFragment(cl)
        mapFragment = MapFragment()
        list.add(mapFragment!!)
        list.add(search!!)

        list.add(SettingsFragment())
        adapter = HomePagerAdapter(requireActivity(), list)
        viewPager!!.adapter = adapter
        viewPager!!.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottom_nav!!.selectedItemId=position
            }
        }
        )

    }


    fun selectItem(i: Int)
    {
        viewPager!!.currentItem = i
    }

    fun search(term:String)
    {
            nvm.search(term)
    }


}