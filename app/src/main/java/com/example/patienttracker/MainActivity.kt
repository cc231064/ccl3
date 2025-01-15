package com.example.patienttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.patienttracker.navigation.NavGraphSetup
import com.example.patienttracker.presentation.ui.theme.PatientTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

// Main activity of the app
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatientTrackerTheme {
                // Create a NavController for navigation
                val navController = rememberNavController()
                // Set up the navigation graph
                NavGraphSetup(navController = navController)
            }
        }
    }
}