package com.quantrics.yelp.view.fragment

import android.os.Bundle
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
import com.quantrics.yelp.view.adapter.HomePagerAdapter

class ViewPagerFragment:Fragment {

    var viewPager: ViewPager2? = null
    var adapter: HomePagerAdapter? = null

    var bottom_nav: BottomNavigationView? = null

    constructor(bottom_nav: BottomNavigationView):this()
    {
        this.bottom_nav = bottom_nav
    }

    constructor()
    {}

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
        setFragments()
    }

    fun setFragments() {
        var list: ArrayList<Fragment> = ArrayList()
        list.add(SearchResultFragment())
        list.add(MapFragment())
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


}