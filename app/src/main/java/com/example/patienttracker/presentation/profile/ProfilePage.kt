package com.example.patienttracker.presentation.profile

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patienttracker.domain.model.Profile

@Composable
fun ProfilePage(
    viewModel: ProfileViewModel = viewModel()
) {
    val profile by viewModel.profile.collectAsState()

    profile?.let {
        Text("Name: ${it.name}")
        Text("Email: ${it.email}")
        Text("Phone: ${it.phone}")
        Text("Address: ${it.address}")
        Text("Further Info: ${it.someOtherField}")
    } ?: run {
        Text("No profile found")

    }
}