package com.hesun.comp304lab4_ex1

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
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

    private lateinit var workManager: WorkManager

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

        workManager = WorkManager.getInstance(applicationContext)
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

                Scaffold(
                    modifier = Modifier.safeContentPadding(),
                    floatingActionButton =
                    {
                        Button(
                            onClick = {
                                var request =
                                    OneTimeWorkRequestBuilder<TestWorker>().build()
                                workManager.enqueue(request)
                            }) {
                            Text("Test")
                        }
                    }

                )
                { paddingValues ->


                    FindLocation()
                    TestGeofencing()

                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Composable
    fun FindLocation() {

        val context = LocalContext.current
        var myLocation by remember { mutableStateOf<LatLng?>(null) }
        val fusedLocationClient =
            remember { LocationServices.getFusedLocationProviderClient(context) }
        val locationRequest =
            remember {
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build()
            } //create a location request with Builder
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

        DisplayMap(myLocation, cameraPositionState)
    }


    @Composable
    fun DisplayMap(userLocation: LatLng?, cameraPositionState: CameraPositionState) {
        GoogleMap(
            modifier = Modifier.safeDrawingPadding(),
            cameraPositionState = cameraPositionState
        ) {
            userLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "Here I am"
                )
            }
        }
    }


    @Composable
    fun TestGeofencing() {
        val context = LocalContext.current
        val geofenceId = "TestGeofence1"
        val geofenceCenter = LatLng(43.9019, -79.3910)
        val geofenceRadius = 200f // 200 meters

        // Setup Geofence when the app starts
        LaunchedEffect(Unit) {
            val geofence = createGeofence(geofenceId, geofenceCenter, geofenceRadius)
            addGeofence(context, geofence)
        }
    }


    override fun onPause() {
        super.onPause()
        var request = OneTimeWorkRequestBuilder<TestWorker>().build()
        workManager.enqueue(request)

    }
}

