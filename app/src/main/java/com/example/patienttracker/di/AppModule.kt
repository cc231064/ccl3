package com.example.patienttracker.di

import android.app.Application
import androidx.room.Room
import com.example.patienttracker.data.local.PatientDatabase
import com.example.patienttracker.data.repository.PatientRepositoryImpl
import com.example.patienttracker.domain.repository.PatientRepository
import com.example.patienttracker.util.Constants.PATIENT_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Hilt module for dependency injection
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides the PatientDatabase instance
    @Provides
    @Singleton
    fun providePatientDatabase(
        app: Application // Application context
    ): PatientDatabase {
        return Room.databaseBuilder(
            app,
            PatientDatabase::class.java,
            PATIENT_DATABASE // Database name
        ).build()
    }

    // Provides the PatientRepository instance
    @Provides
    @Singleton
    fun providePatientRepository(
        db: PatientDatabase // PatientDatabase instance
    ): PatientRepository {
        return PatientRepositoryImpl(db.patientDao)
    }
}