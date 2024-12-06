// Package declaration for the geofence broadcast receiver
package com.hesun.comp304lab4_ex1

// Importing necessary Android and Google Play Services libraries
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

/**
 * Broadcast Receiver for handling Geofence transition events
 *
 * This class receives and processes geofence-related events such as entering
 * or exiting a predefined geographic area (geofence)
 */
class GFBReceiver : BroadcastReceiver() {
    /**
     * Called when a geofence-related broadcast is received
     *
     * @param context The context in which the receiver is running
     * @param intent The intent containing geofence transition information
     */
    override fun onReceive(context: Context, intent: Intent) {
        // Check if the geofencing event contains an error
        if (GeofencingEvent.fromIntent(intent)?.hasError() == true) {
            // Show a toast message if there's a geofence-related error
            Toast.makeText(context, "Geofence error occurred", Toast.LENGTH_SHORT).show()
            return
        }

        // Parse the geofencing event from the received intent
        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        // Get the type of geofence transition (enter or exit)
        val transition = geofencingEvent?.geofenceTransition

        // Handle different geofence transition types
        when (transition) {
            // When entering the geofence area
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                // Log and show a toast when entering the geofence
                Log.d("app", "Entered geofence --- You Are Close To Costco")
                Toast.makeText(context, "Entered geofence", Toast.LENGTH_SHORT).show()
            }
            // When exiting the geofence area
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                // Log and show a toast when exiting the geofence
                Log.d("app", "Exited geofence")
                Toast.makeText(context, "Exited geofence - I don't Care", Toast.LENGTH_SHORT).show()
            }
        }
    }
}