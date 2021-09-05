package com.quantrics.yelp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quantrics.yelp.R
import com.quantrics.yelp.model.Business

class BusinessItemAdapter(): RecyclerView.Adapter<BusinessItemAdapter.ViewHolder>()
{
    var businesses:List<Business> = listOf()
    lateinit var cl:ClickListener


    constructor(cl:ClickListener,businesses:List<Business>):this()
    {
        this.cl = cl
        this.businesses = businesses
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_business,parent,false))

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var b= businesses.get(position)
        holder.tv_title.text=b.name
        holder.tv_rating.text=b.rating.toString()
        holder.iv_poster.setOnClickListener {
            if(cl!=null)
            {
                cl.onSelected(b)
            }
        }
        Glide.with(holder.iv_poster.context)
            .load(b.imageUrl)
            .placeholder(R.drawable.no)
            .into(holder.iv_poster);
    }

    override fun getItemCount(): Int {
        return this.businesses.size
    }


    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        var tv_title:TextView = view.findViewById(R.id.tv_title)
        var tv_rating:TextView = view.findViewById(R.id.tv_rating)
        var iv_poster:ImageView = view.findViewById(R.id.iv_poster)
    }


}