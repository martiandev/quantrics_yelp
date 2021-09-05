package com.quantrics.yelp.view.adapter

import com.quantrics.yelp.model.Business

interface MapRequestListener
{
    abstract fun showOnMap(b: Business)
}