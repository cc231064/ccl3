package com.example.patienttracker.data.repository

import com.example.patienttracker.data.local.PatientDao
import com.example.patienttracker.domain.model.Patient
import com.example.patienttracker.domain.repository.PatientRepository
import kotlinx.coroutines.flow.Flow

// Implementation of the PatientRepository interface
class PatientRepositoryImpl(
    private val dao: PatientDao // Data access object
): PatientRepository {

    // Insert or update a patient
    override suspend fun addOrUpdatePatient(patient: Patient) {
        dao.addOrUpdatePatient(patient)
    }

    // Delete a patient
    override suspend fun deletePatient(patient: Patient) {
        dao.deletePatient(patient)
    }

    // Get a patient by ID
    override suspend fun getPatientById(patientId: Int): Patient? {
        return dao.getPatientById(patientId)
    }

    // Get all patients, observe changes
    override fun getAllPatients(): Flow<List<Patient>> {
        return dao.getAllPatients()
    }
}