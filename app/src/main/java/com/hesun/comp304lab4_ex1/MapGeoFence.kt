package com.hesun.comp304lab4_ex1

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

/**
 * Creates a Geofence object with specified parameters
 *
 * @param geofenceId Unique identifier for the geofence
 * @param latLng Geographic coordinates (latitude and longitude) of the geofence center
 * @param radius Radius of the geofence in meters
 * @return Configured Geofence object
 */
fun createGeofence(
    geofenceId: String,
    latLng: LatLng,
    radius: Float
): Geofence {
    return Geofence.Builder()
        // Set a unique request ID for the geofence
        .setRequestId(geofenceId)
        // Define the circular region with latitude, longitude, and radius
        .setCircularRegion(latLng.latitude, latLng.longitude, radius)
        // Set the geofence to never expire
        .setExpirationDuration(Geofence.NEVER_EXPIRE)
        // Configure transition types to trigger on both entering and exiting the geofence
        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
        .build()
}

/**
 * Builds a GeofencingRequest from a given Geofence
 *
 * @param geofence The Geofence to be added to the request
 * @return Configured GeofencingRequest
 */
fun buildGeofencingRequest(geofence: Geofence): GeofencingRequest {
    return GeofencingRequest.Builder()
        // Set initial trigger to fire when entering the geofence
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        // Add the specific geofence to the request
        .addGeofence(geofence)
        .build()
}

/**
 * Adds a geofence to the geofencing client with necessary permissions and callbacks
 *
 * @param context Application context
 * @param geofence Geofence to be added
 */
fun addGeofence(context: Context, geofence: Geofence) {
    // Initialize the geofencing client
    val geofencingClient = LocationServices.getGeofencingClient(context)

    // Build the geofencing request
    val geofencingRequest = buildGeofencingRequest(geofence)

    // Create a pending intent for the geofence broadcast receiver
    val geofencePendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, GFBReceiver::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    )

    // Check and ensure fine location permission is granted
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // If permission is not granted, exit the function
        return
    }

    // Add the geofence with success and failure listeners
    geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)
        .addOnSuccessListener {
            // Show a toast and log a message when geofence is successfully added
            Toast.makeText(context, "Geofence added", Toast.LENGTH_LONG).show()
            Log.d("app", "Geofence added")
        }
        .addOnFailureListener {
            // Show a toast if geofence addition fails
            Toast.makeText(context, "Failed to add geofence", Toast.LENGTH_LONG).show()
        }
}