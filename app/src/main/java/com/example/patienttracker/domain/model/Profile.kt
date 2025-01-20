package com.example.patienttracker.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.patienttracker.util.Constants.PROFILE_TABLE

@Entity(tableName = PROFILE_TABLE)
data class Profile(
    val name: String,
    val age: String,
    val gender: Int,
    val weight: String,
    val height: String,
    @PrimaryKey(autoGenerate = true)
    val profileId: Int? = null
)
