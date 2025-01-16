package com.example.patienttracker.presentation.patient_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Sorting drawer composable
@Composable
fun SortDrawer(
    currentSortOption: SortOption,
    onSortOptionChange: (SortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            border = BorderStroke(1.dp, Color.White),
            backgroundColor = Color(0xFF246EE9)
        ) {
            Text(
                text = "Sort Patients",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }
        SortOptionRow(
            text = "Name",
            selected = currentSortOption == SortOption.NAME,
            onClick = { onSortOptionChange(SortOption.NAME) },
            selectedColor = Color(0xFF246EE9)
        )
        SortOptionRow(
            text = "Age",
            selected = currentSortOption == SortOption.AGE,
            onClick = { onSortOptionChange(SortOption.AGE) },
            selectedColor = Color(0xFF246EE9)
        )
        SortOptionRow(
            text = "Male",
            selected = currentSortOption == SortOption.MALE,
            onClick = { onSortOptionChange(SortOption.MALE) },
            selectedColor = Color(0xFF246EE9)
        )
        SortOptionRow(
            text = "Female",
            selected = currentSortOption == SortOption.FEMALE,
            onClick = { onSortOptionChange(SortOption.FEMALE) },
            selectedColor = Color(0xFF246EE9)
        )
        SortOptionRow(
            text = "Weight",
            selected = currentSortOption == SortOption.WEIGHT,
            onClick = { onSortOptionChange(SortOption.WEIGHT) },
            selectedColor = Color(0xFF246EE9)
        )
    }
}

// Sorting option row composable
@Composable
private fun SortOptionRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = selectedColor,
                unselectedColor = selectedColor
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}