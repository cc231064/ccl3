package com.example.patienttracker.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.patienttracker.util.Constants.PROFILE_TABLE

@Entity(tableName = PROFILE_TABLE)
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val profileId: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val someOtherField: String
)
