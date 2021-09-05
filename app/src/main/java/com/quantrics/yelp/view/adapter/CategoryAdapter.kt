package com.quantrics.yelp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quantrics.yelp.R
import com.quantrics.yelp.model.Category

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
{

    var categories:List<Category> = listOf()

    constructor(categories:List<Category> ):this()
    {
        this.categories = categories
    }

   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tv_category: TextView = view.findViewById(R.id.tv_category)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tv_category.text=categories.get(position).title
    }

    override fun getItemCount(): Int {
        return categories.size
    }

}