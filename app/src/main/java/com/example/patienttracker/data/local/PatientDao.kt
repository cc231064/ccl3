package com.example.patienttracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.patienttracker.domain.model.Patient
import kotlinx.coroutines.flow.Flow

// DAO interface for patient data access
@Dao
interface PatientDao {
    // Insert or update a patient
    @Upsert
    suspend fun addOrUpdatePatient(patient: Patient)

    // Delete a patient
    @Delete
    suspend fun deletePatient(patient: Patient)

    // Get a patient by ID
    @Query("SELECT * FROM patient_table WHERE patientId = :patientId")
    suspend fun getPatientById(patientId: Int): Patient?

    // Get all patients, observe changes
    @Query("SELECT * FROM patient_table")
    fun getAllPatients(): Flow<List<Patient>>
}