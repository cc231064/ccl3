package com.example.patienttracker.presentation.patient_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patienttracker.domain.model.Patient
import com.example.patienttracker.domain.repository.PatientRepository
import com.example.patienttracker.util.Constants.PATIENT_DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel for the Patient Details screen
@HiltViewModel
class PatientDetailsViewModel @Inject constructor(
    private val repository: PatientRepository, // Repository for patient data
    private val savedStateHandle: SavedStateHandle // Handle to save and retrieve state
) : ViewModel() {

    // State to hold the UI data for the patient details screen
    var state by mutableStateOf(PatientDetailsUiState())
        private set

    // Shared flow to emit UI events (like showing a toast or navigating)
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Variable to hold the current patient ID
    private var currentPatientId: Int? = null

    // Flag to indicate if the screen is in edit mode
    var isEditMode by mutableStateOf(false)
        private set

    // Initialize the ViewModel by fetching patient details
    init {
        val isNewPatient = savedStateHandle.get<Boolean>("isNewPatient") ?: false
        isEditMode = isNewPatient
        fetchPatientDetails()
    }

    // Function to handle UI actions
    fun onAction(event: PatientDetailsEvent) {
        when (event) {
            // Update the name in the state
            is PatientDetailsEvent.EnteredName -> {
                state = state.copy(name = event.name)
            }
            // Update the age in the state
            is PatientDetailsEvent.EnteredAge -> {
                state = state.copy(age = event.age)
            }


            is PatientDetailsEvent.EnteredWeight -> {
                state = state.copy(weight = event.weight)
            }
            // Update the assigned doctor in the state
            is PatientDetailsEvent.EnteredAssignedDoctor -> {
                state = state.copy(doctorAssigned = event.doctor)
            }
            // Update the prescription in the state
            is PatientDetailsEvent.EnteredPrescription -> {
                state = state.copy(prescription = event.prescription)
            }
            // Update the gender to female in the state
            PatientDetailsEvent.SelectedFemale -> {
                state = state.copy(gender = 2)
            }
            // Update the gender to male in the state
            PatientDetailsEvent.SelectedMale -> {
                state = state.copy(gender = 1)
            }
            is PatientDetailsEvent.SaveButton -> {
                viewModelScope.launch {
                    try {
                        savePatient(event.weight)
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: Exception) {
                        // Emit a show toast event with the error message
                        _eventFlow.emit(
                            UiEvent.ShowToast(
                                message = e.message ?: "Couldn't save patient's details."
                            )
                        )
                    }
                }
            }
            PatientDetailsEvent.EditMode -> {
                isEditMode = true
            }
        }
    }


    // Function to save the patient details
    private fun savePatient(weight: Int) {
        val age = state.age.toIntOrNull()
        // Validate the input fields
        when {
            state.name.isEmpty() -> throw TextFieldException("Please enter name.")
            state.age.isEmpty() -> throw TextFieldException("Please enter age.")
            state.weight.isEmpty() -> throw TextFieldException("Please enter weight.")
            state.gender == 0 -> throw TextFieldException("Please select gender")
            state.doctorAssigned.isEmpty() -> throw TextFieldException("Please enter doctor assigned.")
            state.prescription.isEmpty() -> throw TextFieldException("Please enter prescription.")
            age == null || age < 0 || age > 100 -> throw TextFieldException("Please enter valid age.")
        }
        val trimmedName = state.name.trim()
        val trimmedDoctorName = state.doctorAssigned.trim()
        // Launch a coroutine to save the patient details
        viewModelScope.launch {
            repository.addOrUpdatePatient(
                patient = Patient(
                    name = trimmedName,
                    age = state.age,
                    gender = state.gender,
                    weight = weight.toString(),
                    doctorAssigned = trimmedDoctorName,
                    prescription = state.prescription,
                    patientId = currentPatientId
                )
            )
        }
    }

    // Function to fetch patient details based on the patient ID
    private fun fetchPatientDetails() {
        savedStateHandle.get<Int>(key = PATIENT_DETAILS_ARGUMENT_KEY)?.let { patientId ->
            if (patientId != -1) {
                viewModelScope.launch {
                    repository.getPatientById(patientId)?.apply {
                        // Update the state with the fetched patient details
                        state = state.copy(
                            name = name,
                            age = age,
                            weight = weight,
                            gender = gender,
                            doctorAssigned = doctorAssigned,
                            prescription = prescription
                        )
                        currentPatientId = patientId
                    }
                }
            }
        }
    }

    // Sealed class to represent UI events
    sealed class UiEvent {
        // Data class to represent a show toast event
        data class ShowToast(val message: String) : UiEvent()
        // Object to represent a save note event
        data object SaveNote : UiEvent()
    }

}

// Custom exception for text field validation
class TextFieldException(message: String?) : Exception(message)