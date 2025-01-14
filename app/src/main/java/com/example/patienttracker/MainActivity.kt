package com.example.patienttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.navigation.compose.rememberNavController
import com.example.patienttracker.navigation.NavGraphSetup
import com.example.patienttracker.presentation.ui.theme.PatientTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatientTrackerTheme {
                val navController = rememberNavController()
                Text("hello")
                NavGraphSetup(navController = navController)
            }
        }
    }
}
