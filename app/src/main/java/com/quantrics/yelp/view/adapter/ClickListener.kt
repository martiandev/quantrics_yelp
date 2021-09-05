package com.quantrics.yelp.view.adapter

import com.quantrics.yelp.model.Business

interface ClickListener {

    abstract fun onSelected(b: Business)
}