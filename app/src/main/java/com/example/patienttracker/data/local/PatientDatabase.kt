package com.example.patienttracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.patienttracker.domain.model.Patient

// Defines the Room database
@Database(
    entities = [Patient::class], // Specifies the entities (tables) in the database
    version = 2, // Database version
    exportSchema = false
)
abstract class PatientDatabase: RoomDatabase() {

    // Provides access to the PatientDao
    abstract val patientDao: PatientDao
}

