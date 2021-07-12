package com.nativkod.android.weather.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nativkod.android.weather.databinding.DayForcastItemBinding

import com.nativkod.android.weather.models.DayForecastItem


class DayForecastAdapter : ListAdapter<DayForecastItem, DayForecastAdapter.DayForecastItemViewHolder>(DayForecastItemDiffCallback) {

    override fun onBindViewHolder(holder: DayForecastItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayForecastItemViewHolder {
        return DayForecastItemViewHolder.from(parent)
    }

    class DayForecastItemViewHolder constructor(var binding: DayForcastItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(dayForecastItem: DayForecastItem){
            binding.dayForecast = dayForecastItem
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): DayForecastAdapter.DayForecastItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return DayForecastItemViewHolder(DayForcastItemBinding.inflate(layoutInflater)
                )
            }
        }
    }

    companion object DayForecastItemDiffCallback: DiffUtil.ItemCallback<DayForecastItem>(){
        override fun areItemsTheSame(oldItem: DayForecastItem, newItem: DayForecastItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DayForecastItem, newItem: DayForecastItem): Boolean {
            return oldItem.time == newItem.time
        }

    }
}