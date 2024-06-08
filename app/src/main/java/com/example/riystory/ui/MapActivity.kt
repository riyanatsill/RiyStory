package com.example.riystory.ui

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.riystory.R
import com.example.riystory.databinding.ActivityMapBinding
import com.example.riystory.preference.UserManager
import com.example.riystory.viewmodel.FactoryVM
import com.example.riystory.viewmodel.MapVM
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.example.riystory.data.Result
import com.example.riystory.data.response.ListStoryItem

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapBinding
    private lateinit var preferences: UserManager
    private lateinit var myMap: GoogleMap
    private lateinit var factory: FactoryVM
    private val viewModel: MapVM by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProperty()
        getStoryMap()
    }

    private fun setProperty() {
        factory = FactoryVM.getInstance(this)
        preferences = UserManager(this)
    }

    private fun getStoryMap() {
        val token = "Bearer ${preferences.getToken()}"
        viewModel.getWithLocation(1, token).observe(this) { response ->
            when (response) {
                is Result.Loading -> {
                    Log.e(TAG, "Loading..")
                }
                is Result.Success -> {
                    showMarker(response.data.listStory)
                }
                is Result.Error -> {
                }
            }
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun showMarker(listStory: List<ListStoryItem>) {
        for (story in listStory) {
            val latLng = LatLng(story.lat, story.lon)
            myMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .snippet(story.description)
                    .title(story.name)
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        configureMap()
    }

    private fun configureMap() {
        myMap.uiSettings.isZoomControlsEnabled = true
        enableMyLocation()
        setMapStyle()
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            myMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                myMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                enableMyLocation()
            }
        }

    companion object {
        private val TAG = MapActivity::class.simpleName
    }
}