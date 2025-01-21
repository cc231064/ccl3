package com.example.patienttracker.presentation.profile

import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.collect

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileDao: ProfileDao
) : ViewModel() {

    private val _profileData = MutableStateFlow(ProfileData("Default Name", "", "", "", ""))
    val profileData: StateFlow<ProfileData> get() = _profileData

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile.asStateFlow()

    init {
        viewModelScope.launch {
            _profile.value = profileDao.getProfileById(1)
        }
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            profileDao.addOrUpdateProfile(profile)
        }
    }
}