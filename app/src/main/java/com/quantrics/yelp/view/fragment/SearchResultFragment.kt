package com.quantrics.yelp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quantrics.yelp.R
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.model.Business
import com.quantrics.yelp.view.adapter.BusinessItemAdapter
import com.quantrics.yelp.view.adapter.ClickListener

class SearchResultFragment():Fragment() {

    var rv_search: RecyclerView? = null
    var businesses: List<Business> = listOf()
    var adapter: BusinessItemAdapter? = null
    lateinit var listener: ClickListener

    constructor(listener: ClickListener) : this()
    {
        this.listener = listener
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as Yelp).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_search = view.findViewById(R.id.rv_search)
        rv_search!!.layoutManager = LinearLayoutManager(requireActivity())
        if(this.adapter!=null)
        {
            this.rv_search!!.adapter = adapter
        }

    }
    fun updateBusinesses(businesses:List<Business>)
    {
        this.businesses=businesses
        this.adapter = BusinessItemAdapter(listener,this.businesses)
        if(rv_search!=null)
        {
            this.rv_search!!.adapter = adapter
        }


    }


}