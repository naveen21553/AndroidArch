package com.mba.sample.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mba.sample.R
import com.mba.sample.model.Country
import com.mba.sample.utils.loadImage
import com.mba.sample.utils.loadImageProgressDrawableUtil
import org.w3c.dom.Text

class RecyclerListViewAdapter (val countries: ArrayList<Country>) : RecyclerView.Adapter<RecyclerListViewAdapter.CountryViewHolder>() {

    fun update(newCountries: List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
    )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int = countries.size


    class CountryViewHolder (view: View) : RecyclerView.ViewHolder(view){
        private var textViewCountryName = view.findViewById<TextView>(R.id.textViewCountryName)
        private var textViewCapitalName = view.findViewById<TextView>(R.id.textViewCapitalName)
        private var imageViewFlag = view.findViewById<ImageView>(R.id.imageViewFlag)
        private var imageLoaderDrawable = loadImageProgressDrawableUtil(view.context)
        fun bind(country: Country){
            textViewCountryName.text = country.countryName
            textViewCapitalName.text = country.capitalName

            imageViewFlag.loadImage(country.flagPNG, imageLoaderDrawable)
        }
    }
}