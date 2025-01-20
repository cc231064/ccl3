package com.example.patienttracker.domain.repository

import com.example.patienttracker.domain.model.Profile

interface ProfileRepository {

    suspend fun addOrUpdateProfile(profile: Profile)
    suspend fun deleteProfile(profile: Profile)
    suspend fun getProfileById(profileId: Int): Profile?
}