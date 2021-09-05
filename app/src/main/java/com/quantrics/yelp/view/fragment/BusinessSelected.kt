package com.quantrics.yelp.view.fragment

import com.quantrics.yelp.model.Business

interface BusinessSelected
{
    abstract fun onSelected(b: Business)
}