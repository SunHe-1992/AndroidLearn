package com.hesun.comp304lab4_ex1

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hesun.comp304lab4_ex1.ui.theme.Hesun_COMP304Lab4_Ex1Theme

class MainActivity : ComponentActivity() {

    val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
//            &&
//            permissions[Manifest.permission.ACCESS_BACKGROUND_LOCATION] == true &&
//            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            // Permissions granted
            //Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()

        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //ask permission for location
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
        setContent {
            Hesun_COMP304Lab4_Ex1Theme {
                var context = LocalContext.current
                var Toronto = LatLng(43.651070, -79.347015)
                val camPos = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(Toronto, 10f)

                }
                Scaffold(
                    modifier = Modifier.safeContentPadding(),
                )
                { paddingValues ->

                    Button(onClick = {}) {
                        Text("button test")
                    }

                    GoogleMapComposeApp()
//            GoogleMap(
//                modifier = Modifier.safeContentPadding(),
//                cameraPositionState = camPos
//            ) { }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Composable
    fun GoogleMapComposeApp() {

        val context = LocalContext.current
        var myLocation by remember { mutableStateOf<LatLng?>(null) }
        val fusedLocationClient =
            remember { LocationServices.getFusedLocationProviderClient(context) }
        val locationRequest =
            remember { LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build() } //create a location request with Builder
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(.0, .0), 15f)
        }
        // Request location updates
        LaunchedEffect(Unit) {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let { location ->
                        val newLocation = LatLng(location.latitude, location.longitude)
                        myLocation = newLocation
                        cameraPositionState.move(
                            CameraUpdateFactory.newLatLng(newLocation)
                        )
                    }
                }
            }

            if (myLocation == null) {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    context.mainLooper
                )
            }
        }

        GoogleMapView(myLocation, cameraPositionState)
    }


    @Composable
    fun GoogleMapView(userLocation: LatLng?, cameraPositionState: CameraPositionState) {
        GoogleMap(
            modifier = Modifier.safeDrawingPadding(),
            cameraPositionState = cameraPositionState
        ) {
            userLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "You are here"
                )
            }
        }
    }

}

