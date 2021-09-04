package com.quantrics.yelp.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.quantrics.yelp.R
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.network.NetworkViewModel
import javax.inject.Inject

class SearchResultFragment:Fragment() {


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

    }
}