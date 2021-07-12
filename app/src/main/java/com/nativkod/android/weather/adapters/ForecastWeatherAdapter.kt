package com.nativkod.android.weather.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.nativkod.android.weather.databinding.ForecastItemBinding
import com.nativkod.android.weather.helpers.SpanningLinearLayoutManager
import com.nativkod.android.weather.models.ForecastListItem

class ForecastWeatherAdapter(val context: Context): ListAdapter<ForecastListItem,ForecastWeatherAdapter.ForecastWeatherViewHolder> (ForecastListItemDiffCallback) {

    override fun onBindViewHolder(holder: ForecastWeatherViewHolder, position: Int) {
       holder.bind(getItem(position),context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastWeatherViewHolder {
       return ForecastWeatherViewHolder.from(parent)
    }

    class ForecastWeatherViewHolder constructor(var binding: ForecastItemBinding) : RecyclerView.ViewHolder(binding.root){

        private lateinit var adapter: DayForecastAdapter
        fun bind(
            forecastListItem: ForecastListItem,
            context: Context
        ){
            binding.forecastWeatherItem = forecastListItem
            binding.linearLayout.setOnClickListener{
                if(forecastListItem.isMoreDetailsShown){
                    forecastListItem.isMoreDetailsShown = false
                    binding.dayDetails.visibility = View.GONE
                }else{
                    forecastListItem.isMoreDetailsShown = true
                    binding.dayDetails.visibility = View.VISIBLE
                }
            }
            adapter = DayForecastAdapter()
            val layoutManager =  SpanningLinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            binding.dayForecastList.layoutManager = layoutManager
            binding.dayForecastList.itemAnimator = DefaultItemAnimator()
            binding.dayForecastList.adapter = adapter
            adapter.submitList(forecastListItem.dayForecastList)
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ForecastWeatherViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                return ForecastWeatherViewHolder(ForecastItemBinding.inflate(layoutInflater))
            }
        }
    }

    companion object ForecastListItemDiffCallback: DiffUtil.ItemCallback<ForecastListItem>(){
        override fun areItemsTheSame(oldItem: ForecastListItem, newItem: ForecastListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ForecastListItem, newItem: ForecastListItem): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

    }
}