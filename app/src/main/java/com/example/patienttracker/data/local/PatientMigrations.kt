package com.example.patienttracker.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.patienttracker.util.Constants.PATIENT_TABLE

object PatientMigrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE $PATIENT_TABLE ADD COLUMN weight TEXT NOT NULL DEFAULT ''")
        }
    }
}