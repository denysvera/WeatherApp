package com.nativkod.android.weather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nativkod.android.weather.R
import com.nativkod.android.weather.adapters.ForecastWeatherAdapter
import com.nativkod.android.weather.database.AppDatabase
import com.nativkod.android.weather.databinding.HomeFragmentBinding
import com.nativkod.android.weather.helpers.GPS_REQUEST
import com.nativkod.android.weather.helpers.GpsUtils
import com.nativkod.android.weather.helpers.LOCATION_REQUEST
import com.nativkod.android.weather.models.DayForecastItem
import com.nativkod.android.weather.network.OpenWeatherApi
import com.nativkod.android.weather.repository.WeatherAppRepository

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var adapter: ForecastWeatherAdapter
    private var isGPSEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment,container, false)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val  database = AppDatabase.getDatabase(application)

        val weatherAppRepository =  WeatherAppRepository(database!!, OpenWeatherApi.retrofitService)
        val viewModelFactory = ViewModelFactory(weatherAppRepository,application)
        viewModel  = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        GpsUtils(requireContext()).turnGPSOn(object : GpsUtils.OnGpsListener {

            override fun gpsStatus(isGPSEnable: Boolean) { isGPSEnabled = isGPSEnable
            }
        })

       // viewModel.refreshCurrentLocationWeather("49.974737","-98.2955146")
        viewModel.currentWeather.observe(viewLifecycleOwner, Observer {
            if(it !=null) {
                binding.lastUpdated.text = it.lastUpdated
                val weather = it.weather[0]
                when (weather.main) {
                    "Clear" -> {
                        binding.weatherImage.setImageResource(R.drawable.forest_sunny)
                        binding.layout.setBackgroundColor(resources.getColor(R.color.sunny, null))
                    }
                    "Clouds" -> {
                        binding.weatherImage.setImageResource(R.drawable.forest_cloudy)
                        binding.layout.setBackgroundColor(resources.getColor(R.color.cloudy, null))
                    }
                    else -> {
                        binding.weatherImage.setImageResource(R.drawable.forest_rainy)
                        binding.layout.setBackgroundColor(resources.getColor(R.color.rainy, null))
                    }
                }
            }
        })
        adapter = ForecastWeatherAdapter(requireContext())
        binding.forecastList.adapter = adapter
        viewModel.currentForecast.observe(viewLifecycleOwner, Observer {
            if (it !=null){
                val shortList = it.list.distinctBy{ it.getDay()}
                for(item in shortList){
                    item.dayForecastList = it.toDomainForecastWeather().getDayForecast(item.getDay())
                }
                adapter.submitList(shortList)
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer {
            when(it){
                WeatherUpdateStatus.LOADING->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                WeatherUpdateStatus.DONE->{
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }
    private fun invokeLocationAction() {
        when {
            !isGPSEnabled ->Toast.makeText(requireContext(),getString(R.string.enable_gps),Toast.LENGTH_LONG).show()

            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() -> Toast.makeText(requireContext(),getString(R.string.permission_request),Toast.LENGTH_LONG).show()

            else -> ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST
            )
        }
    }

    private fun startLocationUpdate() {
        viewModel.getLocationData().observe(this, Observer {
            viewModel.refreshCurrentLocationWeather(it.latitude.toString(),it.longitude.toString())
            viewModel.refreshCurrentLocationForecast(it.latitude.toString(),it.longitude.toString())
        })
    }
    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }
}