package com.example.patienttracker.domain.repository

import com.example.patienttracker.domain.model.Profile

interface ProfileRepository {

    // Method to add or update a profile
    suspend fun addOrUpdateProfile(profile: Profile)

    // Method to delete a profile
    suspend fun deleteProfile(profile: Profile)

    // Method to get a profile by ID
    suspend fun getProfileById(profileId: Int): Profile?
}
