package com.nativkod.android.weather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nativkod.android.weather.R
import com.nativkod.android.weather.databinding.ForecastItemBinding
import com.nativkod.android.weather.models.ForecastListItem
import com.nativkod.android.weather.models.ForecastWeather

class ForecastWeatherAdapter: ListAdapter<ForecastListItem,ForecastWeatherAdapter.ForecastWeatherViewHolder> (ForecastListItemDiffCallback) {

    override fun onBindViewHolder(holder: ForecastWeatherViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastWeatherViewHolder {
       return ForecastWeatherViewHolder.from(parent)
    }

    class ForecastWeatherViewHolder constructor(var binding: ForecastItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(forecastListItem: ForecastListItem){
            binding.forecastWeatherItem = forecastListItem
            val weather = forecastListItem.weather[0]
            when (weather.main) {
                "Clear" -> {
                    binding.weatherIcon .setImageResource(R.drawable.clear)
                }
                "Clouds" -> {
                    binding.weatherIcon.setImageResource(R.drawable.partly_sunny)
                }
                else -> {
                    binding.weatherIcon.setImageResource(R.drawable.rain)
                }
            }
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