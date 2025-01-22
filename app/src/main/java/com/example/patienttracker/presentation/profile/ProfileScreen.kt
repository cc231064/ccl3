package com.example.patienttracker.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.patienttracker.domain.model.Profile

@Composable
fun ProfileScreen(
    navController: NavHostController,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    viewModel: ProfileViewModel = viewModel() // Inject the ViewModel
) {
    // Observe the profile state from the ViewModel
    val profile by viewModel.profile.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Content of the profile screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp), // Adding padding for better layout
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Check if profile is not null before displaying
            profile?.let {
                Text(text = "Name: ${it.name}", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp)) // Add space between elements
                Text(text = "Email: ${it.email}", style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Phone: ${it.phone}", style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Address: ${it.address}", style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Further Info: ${it.someOtherField}", style = MaterialTheme.typography.body1)

                Spacer(modifier = Modifier.height(16.dp)) // Space before the button
                Button(
                    onClick = onEditClick, // Call the edit click lambda
                    modifier = Modifier.align(Alignment.End) // Align button to the end
                ) {
                    Text("Edit Profile")
                }
            } ?: run {
                // Handle the case where the profile is null
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No profile data available.", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Please create a new profile to get started.")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onEditClick,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Create Profile")
                    }
                }
            }
        }
    }
}
