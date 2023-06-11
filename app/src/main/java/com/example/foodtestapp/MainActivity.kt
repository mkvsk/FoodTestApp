package com.example.foodtestapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.foodtestapp.ui.viewmodel.BagViewModel
import com.example.foodtestapp.ui.viewmodel.DishesViewModel
import com.example.foodtestapp.ui.viewmodel.FoodCategoriesViewModel
import com.example.foodtestapp.ui.viewmodel.UserViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import online.example.foodtestapp.R
import online.example.foodtestapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var bagViewModel: BagViewModel
    private lateinit var dishViewModel: DishesViewModel
    private lateinit var foodCategoriesViewModel: FoodCategoriesViewModel
    private lateinit var userViewModel: UserViewModel

    val PERMISSION_ID = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateViewModels()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, navController)
            return@setOnItemSelectedListener true
        }
        getLastLocation()
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
        } else {
            val geocoder = Geocoder(this, Locale.getDefault())
            val mLocationRequest: LocationRequest = LocationRequest.create()
            mLocationRequest.interval = 6000
            mLocationRequest.fastestInterval = 500
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val mLocationCallback: LocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        location?.let { it ->
                            val fromLocation = geocoder.getFromLocation(
                                it.latitude,
                                it.longitude,
                                1
                            )
                            if (!fromLocation.isNullOrEmpty()) {
                                fromLocation[0].locality?.let { city ->
                                    userViewModel.setLocationCity(city)
                                }
                            }
                        }
                    }
                }
            }
            LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(mLocationRequest, mLocationCallback, null)
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), PERMISSION_ID
        )
    }

    private fun instantiateViewModels() {
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        bagViewModel = ViewModelProvider(this)[BagViewModel::class.java]
        dishViewModel = ViewModelProvider(this)[DishesViewModel::class.java]
        foodCategoriesViewModel = ViewModelProvider(this)[FoodCategoriesViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}