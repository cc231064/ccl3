package com.example.patienttracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patienttracker.presentation.intro.IntroScreen
import com.example.patienttracker.presentation.patient_details.PatientDetailsScreen
import com.example.patienttracker.presentation.patient_list.PatientListScreen
import com.example.patienttracker.util.Constants.PATIENT_DETAILS_ARGUMENT_KEY

// Defines the navigation routes
sealed class Screen(val route: String) {
    object Intro : Screen("intro_screen")
    object PatientList : Screen("patient_list_screen")
    object PatientDetails : Screen(
        "patient_details_screen?$PATIENT_DETAILS_ARGUMENT_KEY=" +
                "{$PATIENT_DETAILS_ARGUMENT_KEY}&isNewPatient={isNewPatient}"
    ) {
        fun passPatientId(patientId: Int? = null, isNewPatient: Boolean = false): String {
            return "patient_details_screen?$PATIENT_DETAILS_ARGUMENT_KEY=$patientId&isNewPatient=$isNewPatient"
        }
    }
}

// Sets up the navigation graph
@Composable
fun NavGraphSetup(
    navController: NavHostController // Navigation controller
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Intro.route // Start with IntroScreen
    ) {
        composable(route = Screen.Intro.route) {
            IntroScreen(
                onContinueClick = {
                    // Navigate to the PatientListScreen after the intro screen
                    navController.navigate(Screen.PatientList.route)
                }
            )
        }
        composable(route = Screen.PatientList.route) {
            PatientListScreen(
                onFabClick = { isNewPatient ->
                    // Navigate to PatientDetailsScreen for a new patient
                    navController.navigate(Screen.PatientDetails.passPatientId(isNewPatient = isNewPatient))
                },
                onItemClick = { patientId, isNewPatient ->
                    // Navigate to PatientDetailsScreen with patient ID
                    navController.navigate(Screen.PatientDetails.passPatientId(patientId, isNewPatient))
                }
            )
        }
        composable(
            route = Screen.PatientDetails.route,
            arguments = listOf(
                navArgument(PATIENT_DETAILS_ARGUMENT_KEY) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("isNewPatient") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { entry ->
            PatientDetailsScreen(
                onBackClick = { navController.navigateUp() },
                onSuccessfulSaving = { navController.navigateUp() }
            )
        }
    }
}