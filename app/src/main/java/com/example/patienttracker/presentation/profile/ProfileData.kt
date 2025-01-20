package com.example.patienttracker.presentation.profile

import androidx.room.PrimaryKey

data class ProfileData(
    val name: String,
    val age: String,
    val gender: Int,
    val weight: String,
    val height: String,
)

