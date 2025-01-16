package com.example.patienttracker.presentation.patient_details

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

// Patient details screen
@Composable
fun PatientDetailsScreen(
    onBackClick: () -> Unit,
    onSuccessfulSaving: () -> Unit,
    viewModel: PatientDetailsViewModel = hiltViewModel()
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    // Collect UI events
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                PatientDetailsViewModel.UiEvent.SaveNote -> {
                    onSuccessfulSaving()
                    Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show()
                }
                is PatientDetailsViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Request focus on the first text field if in edit mode
    LaunchedEffect(key1 = viewModel.isEditMode) {
        if (viewModel.isEditMode) {
            delay(500)
            focusRequester.requestFocus()
        }
    }

    val state = viewModel.state

    // Main content of the screen
    Scaffold(
        topBar = {
            TopBar(
                onBackClick = onBackClick,
                isEditMode = viewModel.isEditMode,
                onEditClick = { viewModel.onAction(PatientDetailsEvent.EditMode) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
        ) {
            // Name text field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = state.name,
                onValueChange = { newValue ->
                    viewModel.onAction(PatientDetailsEvent.EnteredName(newValue))
                },
                label = { Text(text = "Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                enabled = viewModel.isEditMode
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Age and gender row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Age text field
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = state.age,
                    onValueChange = { newValue ->
                        viewModel.onAction(PatientDetailsEvent.EnteredAge(newValue))
                    },
                    label = { Text(text = "Age") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                    enabled = viewModel.isEditMode
                )

                Spacer(modifier = Modifier.width(10.dp))
                // Male radio button

                RadioGroup(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Male",
                    selected = state.gender == 1,
                    onClick = { if (viewModel.isEditMode) viewModel.onAction(PatientDetailsEvent.SelectedMale) }
                )
                // Female radio button
                RadioGroup(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Female",
                    selected = state.gender == 2,
                    onClick = { if (viewModel.isEditMode) viewModel.onAction(PatientDetailsEvent.SelectedFemale) }
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.weight,
                onValueChange = { newValue ->
                    viewModel.onAction(PatientDetailsEvent.EnteredWeight(newValue))
                },
                label = { Text(text = "Weight (kg)") },
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                enabled = viewModel.isEditMode
            )

            Spacer(modifier = Modifier.height(10.dp))
            // Assigned doctor text field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.doctorAssigned,
                onValueChange = { newValue ->
                    viewModel.onAction(PatientDetailsEvent.EnteredAssignedDoctor(newValue))
                },
                label = { Text(text = "Assigned Doctor's Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                enabled = viewModel.isEditMode
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Prescription text field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = state.prescription,
                onValueChange = { newValue ->
                    viewModel.onAction(PatientDetailsEvent.EnteredPrescription(newValue))
                },
                label = { Text(text = "Prescription") },
                enabled = viewModel.isEditMode
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Save button
            if (viewModel.isEditMode) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val weight = state.weight.toIntOrNull() ?: 0
                        viewModel.onAction(PatientDetailsEvent.SaveButton(weight))
                    }
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// Top app bar
@Composable
fun TopBar(
    onBackClick: () -> Unit,
    isEditMode: Boolean,
    onEditClick: () -> Unit
){
    TopAppBar(
        title = {
            Text(
                text = if (isEditMode) "Edit Patient Details" else "Patient Details",
                style = MaterialTheme.typography.h5
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            if (!isEditMode) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
        }
    )
}

// Radio button group
@Composable
private fun RadioGroup(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primary)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body1
        )
    }
}