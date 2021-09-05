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
import com.quantrics.yelp.model.Open
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAdapter(): RecyclerView.Adapter<ScheduleAdapter.ViewHolder>()
{
    var open:List<Open> = listOf()


    constructor( open:List<Open> ):this()
    {
        this.open = open
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType==0)
        {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hours,parent,false))

        }
        else
        {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hours_now,parent,false))

        }

    }

    override fun getItemViewType(position: Int): Int
    {
        var f = SimpleDateFormat("EEE")
        var d:String = f.format(Date()).uppercase()

        if(open.get(position).day==0&&d.equals("MON"))
        {
            return 1
        }
        else  if(open.get(position).day==1&&d.equals("TUE"))
        {
            return 1
        }
        else  if(open.get(position).day==2&&d.equals("WED"))
        {
            return 1
        }
        else  if(open.get(position).day==3&&d.equals("THU"))
        {
            return 1
        }
        else  if(open.get(position).day==4&&d.equals("FRI"))
        {
            return 1
        }
        else  if(open.get(position).day==5&&d.equals("SAT"))
        {
            return 1
        }
        else if(open.get(position).day==6&&d.equals("SUN"))
        {
            return 1
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      var o = open.get(position)
        holder.tv_day!!.text = when(o.day) {
            0 -> "MONDAY"
            1 -> "TUESDAY"
            2 -> "WEDNESDAY"
            3 -> "THURSDAY"
            4 -> "FRIDAY"
            5 -> "SATURDAY"
            6 -> "SUNDAY"
            else -> "TODAY"

        }
        var dateFormat =SimpleDateFormat("kkmm")
        var df2 =SimpleDateFormat("hh:mm a")
        holder.tv_open!!.text="OPEN:"+df2.format(dateFormat.parse(o.start))
        holder.tv_close!!.text="CLOSE:"+df2.format(dateFormat.parse(o.end))
    }

    override fun getItemCount(): Int {
        return this.open.size
    }


    inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        var tv_day:TextView = view.findViewById(R.id.tv_day)
        var tv_open:TextView = view.findViewById(R.id.tv_open)
        var tv_close:TextView = view.findViewById(R.id.tv_close)

    }


}