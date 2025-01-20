package com.example.patienttracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patienttracker.presentation.intro.IntroScreen
import com.example.patienttracker.presentation.patient_details.PatientDetailsScreen
import com.example.patienttracker.presentation.patient_list.PatientListScreen
import com.example.patienttracker.presentation.profile.ProfileData
import com.example.patienttracker.presentation.profile.ProfileScreen
import com.example.patienttracker.presentation.profile.ProfileViewModel
import com.example.patienttracker.util.Constants.PATIENT_DETAILS_ARGUMENT_KEY
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlin.coroutines.CoroutineContext

// Defines the navigation routes
sealed class Screen(val route: String) {
    data object Intro : Screen("intro_screen")
    data object PatientList : Screen("patient_list_screen")
    data object PatientDetails : Screen(
        "patient_details_screen?$PATIENT_DETAILS_ARGUMENT_KEY=" +
                "{$PATIENT_DETAILS_ARGUMENT_KEY}&isNewPatient={isNewPatient}"
    ) {
        fun passPatientId(patientId: Int? = null, isNewPatient: Boolean = false): String {
            return "patient_details_screen?$PATIENT_DETAILS_ARGUMENT_KEY=$patientId&isNewPatient=$isNewPatient"
        }
    }
    data object ProfileScreen : Screen("profile")
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
                navController = navController,
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
        composable(route = Screen.ProfileScreen.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            val profileData by viewModel.profileData.collectAsState(initial = ProfileData("Default Name", "", "", "", ""))

            ProfileScreen(
                navController = navController,
                profileData = profileData ?: ProfileData("Default Name", "", "", "", "")
            )
        }
    }
}

