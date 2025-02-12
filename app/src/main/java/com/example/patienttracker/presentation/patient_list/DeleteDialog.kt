package com.example.patienttracker.presentation.patient_list

import android.util.Log
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

// Delete confirmation dialog
@Composable
fun DeleteDialog(
    title: String,
    message: String,
    onDialogDismiss: () -> Unit,
    confirmButtonClicked: () -> Unit
) {
    AlertDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.body1
            )
        },
        confirmButton = {
            TextButton(onClick = {
                confirmButtonClicked()
                onDialogDismiss()
            }) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDialogDismiss) {
                Text(text = "No")
            }
        },
        onDismissRequest = onDialogDismiss
    )
}

fun deleteThePatient() {
    Log.d("DeleteDialog", "deleteThePatient: called")
}