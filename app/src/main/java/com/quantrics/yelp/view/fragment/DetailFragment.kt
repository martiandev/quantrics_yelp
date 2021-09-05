package com.quantrics.yelp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.quantrics.yelp.R
import com.quantrics.yelp.model.Business
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.network.NetworkViewModel
import com.quantrics.yelp.view.adapter.CategoryAdapter
import com.quantrics.yelp.view.adapter.ScheduleAdapter
import javax.inject.Inject


class DetailFragment():Fragment(){

    @Inject
    lateinit var nvm:NetworkViewModel
    var b:Business?=null
    lateinit var iv_poster:ImageView
    lateinit var tv_rating:TextView
    lateinit var bt_call:TextView
    lateinit var tv_address:TextView
    lateinit var rv_category:RecyclerView
    lateinit var rv_schedule:RecyclerView


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as Yelp).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_poster = view.findViewById(R.id.iv_poster)
        tv_rating = view.findViewById(R.id.tv_rating)
        tv_address = view.findViewById(R.id.tv_address)
        bt_call = view.findViewById(R.id.bt_call)
        rv_category = view.findViewById(R.id.rv_category)
        rv_category!!.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        rv_schedule = view.findViewById(R.id.rv_schedule)
        rv_schedule!!.layoutManager = LinearLayoutManager(requireActivity())

        updateUI()
    }

    fun updateUI()
    {
        nvm.detail.observe(requireActivity(), Observer {


            if(it.hours!=null)
                {
                    if(it.hours.size>0)
                    {
                        for(h in it.hours)
                        {
                            if(h.hours.size>0)
                            {
                               var scheduleAdapter = ScheduleAdapter(h.hours.toList())
                                rv_schedule.adapter=scheduleAdapter
                            }
                            else
                            {
                                Log.i("HOURS","nd1")
                            }

                        }

                    }
                    else
                    {
                        android.util.Log.i("HOURS","nd2")
                    }
                }

        })
        nvm.searchDetail(b!!.id!!)

        Glide.with(requireActivity())
            .load(b!!.imageUrl)
            .placeholder(R.drawable.no)
            .into(iv_poster);
        tv_rating!!.text = "Rating: "+b!!.rating+" ("+b!!.reviewCount+")"
        bt_call!!.text = "Call "+b!!.displayPhone
        bt_call!!.setOnClickListener{

            val call: Uri = Uri.parse("tel:"+b!!.phone)
            val surf = Intent(Intent.ACTION_DIAL, call)
            startActivity(surf)
        }
        rv_category!!.adapter = CategoryAdapter(b!!.categories.toList())



        tv_address!!.text=b!!.location!!.getAddress()+" ("+Math.round(b!!.distance!!/1000*100)/100.0+"km)"
    }
}