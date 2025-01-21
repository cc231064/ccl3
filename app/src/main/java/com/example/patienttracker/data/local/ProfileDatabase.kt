package com.example.patienttracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.patienttracker.domain.model.Profile

@Database(
    entities = [Profile::class],
    version = 1,
    exportSchema = false
)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}

