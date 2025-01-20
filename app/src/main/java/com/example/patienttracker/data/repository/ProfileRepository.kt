package com.example.patienttracker.data.repository

import com.example.patienttracker.data.local.ProfileDao
import com.example.patienttracker.domain.model.Profile
import com.example.patienttracker.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

data class ProfileRepository(
    private val dao: ProfileDao
): ProfileRepository {

    override suspend fun addOrUpdateProfile(profile: Profile) {
        dao.addOrUpdateProfile(profile)
    }

    override suspend fun deleteProfile(profile: Profile) {
        dao.deleteProfile(profile)
    }

    override suspend fun getProfileById(profileId: Int): Profile? {
        return dao.getProfileById(profileId)
    }
}
