package com.example.patienttracker.di

import android.app.Application
import android.content.Context
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
import com.example.patienttracker.data.local.PatientMigrations
import com.example.patienttracker.data.local.ProfileDao
import com.example.patienttracker.data.local.ProfileDatabase
import com.example.patienttracker.data.repository.ProfileRepositoryImpl
import com.example.patienttracker.domain.repository.ProfileRepository
import com.example.patienttracker.util.Constants.PROFILE_DATABASE
import dagger.hilt.android.qualifiers.ApplicationContext

// Hilt module for dependency injection
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }


    // Provides the PatientDatabase instance
    @Provides
    @Singleton
    fun providePatientDatabase(
        app: Application // Application context
    ): PatientDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            PatientDatabase::class.java,

            PATIENT_DATABASE // Database name
        ).addMigrations(PatientMigrations.MIGRATION_1_2)
            .build()
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

    @Module
    @InstallIn(SingletonComponent::class)
    object DatabaseModule {
        @Provides
        @Singleton
        fun provideDatabase(context: Context): ProfileDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ProfileDatabase::class.java,
                PROFILE_DATABASE
            ).fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        fun provideProfileDao(database: ProfileDatabase): ProfileDao {
            return database.profileDao()
        }

        @Provides
        @Singleton
        fun provideProfileRepository(
            db: ProfileDatabase
        ): ProfileRepository {
            return ProfileRepositoryImpl(db.profileDao())
        }
    }

