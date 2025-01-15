package com.example.patienttracker.presentation.patient_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

// Patient list screen
@Composable
fun PatientListScreen(
    onFabClick: () -> Unit,
    onItemClick: (Int?) -> Unit,
    viewModel: PatientListViewModel = hiltViewModel()
) {

    val patientList by viewModel.patientList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = { ListAppBar() },
        floatingActionButton = {
            ListFab(onFabClick = onFabClick)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search text field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text(text = "Search Patient") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                }
            )
            // Patient list
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(patientList) { patient ->
                    PatientItem(
                        patient = patient,
                        onItemClick = { onItemClick(patient.patientId) },
                        onDeleteConfirm = { viewModel.deletePatient(patient) }
                    )
                }
            }
            // Empty list message
            if (patientList.isEmpty() && !viewModel.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Add Patients Details\nby pressing add button.",
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// Top app bar
@Composable
fun ListAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "PatientEase!",
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
            )
        }
    )
}

// Floating action button
@Composable
fun ListFab(
    onFabClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onFabClick
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Patient Button"
        )
    }
}

@Preview
@Composable
fun PatientListScreenPrev() {
    ListAppBar()
}