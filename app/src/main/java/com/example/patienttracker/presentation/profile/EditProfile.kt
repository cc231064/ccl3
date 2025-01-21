package com.example.patienttracker.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patienttracker.domain.model.Profile

@Composable
fun EditProfileScreen(
    navController: NavHostController,
    profileData: Profile, // This is the Profile object passed from NavGraphSetup
    onSaveClick: (Profile) -> Unit // Lambda to handle save action
) {
    // State variables to hold the editable profile data
    var name by remember { mutableStateOf(profileData.name) }
    var email by remember { mutableStateOf(profileData.email) }
    var phone by remember { mutableStateOf(profileData.phone) }
    var address by remember { mutableStateOf(profileData.address) }
    var someOtherField by remember { mutableStateOf(profileData.someOtherField) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = someOtherField,
                onValueChange = { someOtherField = it },
                label = { Text("Further Info") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val updatedProfile = Profile(
                        profileId = profileData.profileId, // Keep the existing ID
                        name = name,
                        email = email,
                        phone = phone,
                        address = address,
                        someOtherField = someOtherField
                    )
                    onSaveClick(updatedProfile) // Pass the updated profile back
                }
            ) {
                Text("Save Changes")
            }
        }
    }
}
