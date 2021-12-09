package ro.alexmamo.firebaseanonymousauthentication.presentation.profile

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.InternalCoroutinesApi
import ro.alexmamo.firebaseanonymousauthentication.core.Constants.TAG
import ro.alexmamo.firebaseanonymousauthentication.domain.model.Response.*
import ro.alexmamo.firebaseanonymousauthentication.presentation.components.ProgressBar
import ro.alexmamo.firebaseanonymousauthentication.presentation.profile.components.ProfileContent
import ro.alexmamo.firebaseanonymousauthentication.presentation.profile.components.ProfileTopBar
import ro.alexmamo.firebaseanonymousauthentication.presentation.util.Screen.AuthScreen

@Composable
@InternalCoroutinesApi
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        profileViewModel.getAuthState()
    }

    Scaffold(
        topBar = {
            ProfileTopBar()
        }
    ) {
        ProfileContent()

        when(val response = profileViewModel.isUserSignedOutState.value) {
            is Loading -> ProgressBar()
            is Success -> {
                if (response.data) {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.route)
                }
            }
            is Error -> Log.d(TAG, response.message)
        }
    }
}