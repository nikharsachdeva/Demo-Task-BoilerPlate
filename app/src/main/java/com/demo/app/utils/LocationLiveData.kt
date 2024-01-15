package com.demo.app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource

class LocationLiveData(context: Context) : LiveData<LocationModel>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val cancellationTokenSource = CancellationTokenSource()

    override fun onInactive() {
        super.onInactive()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        cancellationTokenSource.cancel()
    }


    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.also {
                    setLocationData(it)
                }
            }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {

        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )
        /*
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
         */


    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }

    private fun setLocationData(location: Location) {
        value = LocationModel(
            longitude = location.longitude,
            latitude = location.latitude
        )
    }

    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
//            interval = 10000
//            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

}

data class LocationModel(
    val longitude: Double,
    val latitude: Double,
)

data class AddressModel(
    val longitude: Double,
    val latitude: Double,
    val address1 : String,
    val address2 : String,
    val state : String,
    val pin : String,
    val city : String,
)