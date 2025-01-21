package com.example.patienttracker.data.repository

import com.example.patienttracker.data.local.ProfileDao
import com.example.patienttracker.domain.model.Profile
import com.example.patienttracker.domain.repository.ProfileRepository

class ProfileRepositoryImpl(private val profileDao: ProfileDao) : ProfileRepository {

    override suspend fun addOrUpdateProfile(profile: Profile) {
        profileDao.addOrUpdateProfile(profile)
    }

    override suspend fun deleteProfile(profile: Profile) {
        profileDao.deleteProfile(profile)
    }

    override suspend fun getProfileById(profileId: Int): Profile? {
        return profileDao.getProfileById(profileId)
    }
}
