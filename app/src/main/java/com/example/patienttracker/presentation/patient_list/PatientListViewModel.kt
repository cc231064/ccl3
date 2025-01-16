package com.example.patienttracker.presentation.patient_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patienttracker.domain.model.Patient
import com.example.patienttracker.domain.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel for the patient list screen
@HiltViewModel
class PatientListViewModel @Inject constructor(
    private val repository: PatientRepository // Repository for patient data
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("") // Search query state
    val searchQuery = _searchQuery.asStateFlow()

    private val _currentSortOption = MutableStateFlow(SortOption.NAME) // Current sort option state
    val currentSortOption = _currentSortOption.asStateFlow()

    private val _patientList = MutableStateFlow<List<Patient>>(emptyList()) // Patient list state

    // Filtered and sorted patient list based on search query and sort option
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val patientList: StateFlow<List<Patient>> = combine(
        _searchQuery.debounce(500),
        _currentSortOption
    ) { query, sortOption ->
        Pair(query, sortOption)
    }
        .flatMapLatest { (query, sortOption) ->
            println("flatMapLatest: query = $query, sortOption = $sortOption")
            repository.getAllPatients().map { patients ->
                val filteredList = if (query.isBlank()) {
                    patients
                } else {
                    patients.filter { patient ->
                        patient.name.contains(query, ignoreCase = true) ||
                                patient.doctorAssigned.contains(query, ignoreCase = true)
                    }
                }
                val sortedList = sortPatients(filteredList, sortOption)
                println("sortPatients returned: $sortedList")
                sortedList
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = _patientList.value
        )

    var isLoading by mutableStateOf(false) // Loading state

    // Initialize data
    init {
        viewModelScope.launch {
            isLoading = true
            repository.getAllPatients().collect {
                _patientList.value = it
            }
            isLoading = false
        }
    }

    // Delete a patient
    fun deletePatient(patient: Patient) {
        viewModelScope.launch {
            repository.deletePatient(patient)
        }
    }

    // Update search query
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    // Update sort option
    fun onSortOptionChange(sortOption: SortOption) {
        println("onSortOptionChange: sortOption = $sortOption")
        _currentSortOption.value = sortOption
    }

    // Function to sort the patient list
    private fun sortPatients(patients: List<Patient>, sortOption: SortOption): List<Patient> {
        println("sortPatients called with: sortOption = $sortOption")
        return when (sortOption) {
            SortOption.NAME -> patients.sortedBy { it.name }
            SortOption.AGE -> patients.sortedBy { it.age.toIntOrNull() ?: 0 }
            SortOption.MALE -> patients.filter { it.gender == 1 }
            SortOption.FEMALE -> patients.filter { it.gender == 2 }
            SortOption.WEIGHT -> patients.sortedBy { it.weight.toIntOrNull() ?: 0 }
        }
    }
}