package com.manan.weatherapp

import CCForecastViewModel
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.manan.weatherapp.data.repository.CCWeatherRepositoryImpl
import com.manan.weatherapp.domain.usercases.CCGetFiveDayForecastUseCase
import com.manan.weatherapp.ui.forecast.CCForecastScreen
import com.manan.weatherapp.ui.theme.WeatherAPPTheme

class MainActivity : ComponentActivity() {

    private val m_viewModel by lazy {
        val repository = CCWeatherRepositoryImpl()
        val useCase = CCGetFiveDayForecastUseCase(repository)
        CCForecastViewModel(useCase)
    }

    private lateinit var m_fusedLocationClient: FusedLocationProviderClient

    private val m_locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                loadForecastForCurrentLocation()
            } else {
                m_viewModel.setNoLocationPermission()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        m_fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            WeatherAPPTheme {
                val state by m_viewModel.m_state.collectAsState()

                CCForecastScreen(
                    m_state = state,
                    onRetry = {
                        requestLocationAndLoad()
                    },
                    onOpenSettings = {
                        openAppSettings()
                    }
                )
            }
        }

        requestLocationAndLoad()
    }

    private fun requestLocationAndLoad() {
        val hasPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            loadForecastForCurrentLocation()
        } else {
            m_locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun loadForecastForCurrentLocation() {
        m_fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    m_viewModel.loadForecast(
                        lat = location.latitude,
                        lon = location.longitude
                    )
                } else {
                    m_viewModel.loadForecast(
                        lat = 25.2048,
                        lon = 55.2708
                    )
                }
            }
            .addOnFailureListener {
                m_viewModel.loadForecast(
                    lat = 25.2048,
                    lon = 55.2708
                )
            }
    }

    private fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        startActivity(intent)
    }
}
