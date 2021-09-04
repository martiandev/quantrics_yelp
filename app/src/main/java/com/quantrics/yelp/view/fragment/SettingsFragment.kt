package com.quantrics.yelp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.quantrics.yelp.R
import com.quantrics.yelp.app.Yelp
import com.quantrics.yelp.preference.YelpPreference
import javax.inject.Inject

class SettingsFragment:Fragment() {

    var seekBarRadius:SeekBar? = null
    var tv_radius:TextView? = null
    var switch_open:SwitchMaterial? = null
    var switch_distance: SwitchMaterial? = null
    var switch_rating:SwitchMaterial? = null

    @Inject
    lateinit var preference: YelpPreference



    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as Yelp).appComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.seekBarRadius = view.findViewById(R.id.seekBar_radius)
        this.tv_radius = view.findViewById(R.id.tv_value_radius)
        this.switch_open = view.findViewById(R.id.sw_enable)
        this.switch_distance = view.findViewById(R.id.sw_distance)
        this.switch_rating = view.findViewById(R.id.sw_ratings)
        setValues()
    }

    fun setValues()
    {

        this.seekBarRadius!!.progress = preference.getRadius()!!
        tv_radius!!.text = seekBarRadius!!.progress.toString()
        this.seekBarRadius!!.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv_radius!!.text = p1.toString()
                preference.setRadius(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        this.switch_open!!.isChecked=preference.getOpen()!!
        this.switch_open!!.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->

            preference.setOpen(b)
        }

        this.switch_distance!!.isChecked=preference.getDistance()!!
        this.switch_distance!!.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
            if(b)
            {
                switch_rating!!.isChecked = false
            }
            preference.setDistance(b)
        }
        this.switch_rating!!.isChecked=preference.getRating()!!
        this.switch_rating!!.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
            if(b)
            {
                switch_distance!!.isChecked = false
            }
            preference.setRating(b)
        }
    }
}