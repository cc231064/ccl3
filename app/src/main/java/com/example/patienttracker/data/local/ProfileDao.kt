package com.example.patienttracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.patienttracker.domain.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Upsert
    suspend fun addOrUpdateProfile(profile: Profile)

    @Delete
    suspend fun deleteProfile(profile: Profile)

    @Query("SELECT * FROM profile_table WHERE profileId = :profileId")
    suspend fun getProfileById(profileId: Int): Profile?

}
