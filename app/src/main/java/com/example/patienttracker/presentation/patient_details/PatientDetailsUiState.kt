package com.example.patienttracker.presentation.patient_details

// Represents the UI state for the patient details screen
data class PatientDetailsUiState(
    val name: String = "",
    val age: String = "",
    val gender: Int = 0,
    val doctorAssigned: String = "",
    val prescription: String = ""
)
