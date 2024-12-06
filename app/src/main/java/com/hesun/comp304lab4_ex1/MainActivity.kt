package com.hesun.comp304lab4_ex1

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
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
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hesun.comp304lab4_ex1.ui.theme.Hesun_COMP304Lab4_Ex1Theme

class MainActivity : ComponentActivity() {

    private lateinit var workManager: WorkManager

    // Permission launcher for fine and coarse location permissions
    val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            // Permissions granted
            //Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()

        }
    }

    // Permission launcher specifically for background location access
    val bg_permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_BACKGROUND_LOCATION] == true) {
            // Permissions granted
//            Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            Log.i("app", "ACCESS_BACKGROUND_LOCATION Permissions granted")
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
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        setContent {
            Hesun_COMP304Lab4_Ex1Theme {
                var context = LocalContext.current
                Scaffold(
                    modifier = Modifier.safeContentPadding(),
                )
                { paddingValues ->
                    FindLocation()
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
        // Create location request with high accuracy and 10-second interval
        val locationRequest =
            remember {
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build()
            }
        // Create camera position state
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(.0, .0), 15f)
        }
        // Request location updates
        LaunchedEffect(Unit) {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    // Update location and camera when new location is received
                    locationResult.lastLocation?.let { location ->
                        val newLocation = LatLng(location.latitude, location.longitude)
                        myLocation = newLocation
                        cameraPositionState.move(
                            CameraUpdateFactory.newLatLng(newLocation)
                        )
                    }
                }
            }
            // Request location updates if no location is set
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

    /**
     * Composable function to display Google Map with various interactive features
     * @param userLocation Current user's location
     * @param cameraPositionState State of the map camera
     */
    @Composable
    fun DisplayMap(userLocation: LatLng?, cameraPositionState: CameraPositionState) {
        val context = LocalContext.current
        var isMapTypeToggled by remember { mutableStateOf(false) }
        var markerPosition by remember { mutableStateOf(userLocation) }
        var gfPosition by remember { mutableStateOf(userLocation) }
        var isshowingMarker by remember { mutableStateOf(false) }
        var isshowingGeofence by remember { mutableStateOf(false) }
        var mapProperties by remember {
            mutableStateOf(
                MapProperties(
                    mapType = MapType.NORMAL,
                    isMyLocationEnabled = true
                )
            )
        }
        Column {
            Row(
                modifier = Modifier
                    .safeContentPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isMapTypeToggled) "SATELLITE" else "NORMAL",
                    style = MaterialTheme.typography.bodySmall
                )

                Switch(
                    checked = isMapTypeToggled,
                    onCheckedChange = {
                        isMapTypeToggled = it
                        if (isMapTypeToggled)
                            mapProperties = mapProperties.copy(mapType = MapType.SATELLITE)
                        else
                            mapProperties = mapProperties.copy(mapType = MapType.NORMAL)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary
                    )
                )
                // Button to trigger WorkManager background task
                Button(
                    onClick = {
                        var request = OneTimeWorkRequestBuilder<TestWorker>().build()
                        workManager.enqueue(request)
                    }) {
                    Text(
                        text = "WorkerManager"
                    )

                }

            }
            // Geofence setup button
            Button(onClick = {
                // Check and request background location permission if needed
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    bg_permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
                    )
                } else {
                    // Create and add geofence when permission is granted
                    var location: LatLng = userLocation!!
                    if (location == null)
                        location = LatLng(43.7863, -79.1839)
                    gfPosition = location
                    val geofence = createGeofence("TestGeofence1", userLocation!!, 200f)
                    addGeofence(context, geofence)
                    isshowingGeofence = true
                }
            }) {
                Text(
                    text = "GeoFence"
                )
            }
            // Google Map composable with interactive features
            GoogleMap(
                properties = mapProperties,
                modifier = Modifier.safeDrawingPadding(),
                cameraPositionState = cameraPositionState,
                onMapClick = { clickedLatLng ->
                    // Update marker position & visiblity when map is clicked
                    markerPosition = clickedLatLng
                    isshowingMarker = !isshowingMarker
                },
                onMapLongClick = { clickedLatLng ->
                    //  Toast for location detail
                    Toast.makeText(
                        context,
                        "Detail: \nlatitude: ${clickedLatLng.latitude}\nlongtitude: ${clickedLatLng.longitude}",
                        Toast.LENGTH_SHORT
                    ).show()
                    markerPosition = clickedLatLng
                    isshowingMarker = true
                    cameraPositionState
                }
            ) {
                // Conditionally show marker on map click
                if (isshowingMarker && markerPosition != null) {
                    Marker(
                        state = MarkerState(position = markerPosition!!),
                        title = "Clicked Location",
                        snippet = "Latitude: ${"%.4f".format(markerPosition!!.latitude)}, Longitude: ${
                            "%.4f".format(markerPosition!!.longitude)
                        }",
                        contentDescription = "Clicked Location"
                    )
                }
                // Conditionally show geofence circle
                if (isshowingGeofence && gfPosition != null)
                    Circle(
                        center = gfPosition!!,
                        clickable = true,
                        fillColor = Color.Blue.copy(alpha = 0.3f),
                        radius = 200.0, // Specify the radius in meters
                        strokeColor = Color.Black,
                        strokeWidth = 2f,
                        tag = "geofence",
                        onClick = { circle ->
                        }
                    )
            }
        }
    }


    override fun onPause() {
        super.onPause()
        var request = OneTimeWorkRequestBuilder<TestWorker>().build()
        workManager.enqueue(request)

    }
}

