package com.example.patienttracker.presentation.patient_details

// Sealed class representing patient details events
sealed class PatientDetailsEvent {
    data class EnteredName(val name: String): PatientDetailsEvent()
    data class EnteredAge(val age: String): PatientDetailsEvent()
    data class EnteredWeight(val weight: String): PatientDetailsEvent()
    data class EnteredAssignedDoctor(val doctor: String): PatientDetailsEvent()
    data class EnteredPrescription(val prescription: String): PatientDetailsEvent()
    data class SaveButton(val weight: Int) : PatientDetailsEvent()
    object SelectedMale: PatientDetailsEvent()
    object SelectedFemale: PatientDetailsEvent()
    object EditMode: PatientDetailsEvent()
}