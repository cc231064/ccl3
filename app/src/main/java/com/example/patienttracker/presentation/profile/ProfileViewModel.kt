package com.example.patienttracker.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patienttracker.data.local.ProfileDao
import com.example.patienttracker.domain.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileDao: ProfileDao
) : ViewModel() {

    private val _profileData = MutableStateFlow(ProfileData("Default Name", "", "", "", ""))
    val profileData: StateFlow<ProfileData> get() = _profileData

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile.asStateFlow()

    // Load the profile when the ViewModel is initialized
    init {
        loadProfile(1) // Assuming 1 is the ID of the profile you want to load
    }

    // Load profile data from the database
    private fun loadProfile(profileId: Int) {
        viewModelScope.launch {
            val loadedProfile = profileDao.getProfileById(profileId)
            Log.d("ProfileViewModel", "Loaded Profile: $loadedProfile")
            _profile.value = loadedProfile
            // Update _profileData if loadedProfile is not null
            loadedProfile?.let {
                _profileData.value = ProfileData(
                    name = it.name,
                    email = it.email,
                    phone = it.phone,
                    address = it.address,
                    someOtherField = it.someOtherField
                )
            }
        }
    }

    // Update an existing profile or create a new one
    fun saveProfile(profileData: Profile) {
        viewModelScope.launch {
            val profileToSave = Profile(
                profileId = _profile.value?.profileId ?: 1, // Use existing ID or 0 for new profile
                name = profileData.name,
                email = profileData.email,
                phone = profileData.phone,
                address = profileData.address,
                someOtherField = profileData.someOtherField
            )
            profileDao.addOrUpdateProfile(profileToSave) // Save to the database
            _profile.value = profileToSave
            // Optionally, reload the profile data after saving
            loadProfile(profileToSave.profileId)
        }
    }
}
