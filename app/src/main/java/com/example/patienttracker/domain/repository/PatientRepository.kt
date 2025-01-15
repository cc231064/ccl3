package com.example.patienttracker.domain.repository

import com.example.patienttracker.domain.model.Patient
import kotlinx.coroutines.flow.Flow

// Interface for patient data operations
interface PatientRepository {

    // Insert or update a patient
    suspend fun addOrUpdatePatient(patient: Patient)

    // Delete a patient
    suspend fun deletePatient(patient: Patient)

    // Get a patient by ID
    suspend fun getPatientById(patientId: Int): Patient?

    // Get all patients, observe changes
    fun getAllPatients(): Flow<List<Patient>>
}