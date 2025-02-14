package ro.alexmamo.firebaseanonymousauthentication.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firebaseanonymousauthentication.domain.model.Response.Loading
import ro.alexmamo.firebaseanonymousauthentication.domain.model.Response.Success
import ro.alexmamo.firebaseanonymousauthentication.domain.repository.SignInResponse
import ro.alexmamo.firebaseanonymousauthentication.domain.repository.SignOutResponse
import ro.alexmamo.firebaseanonymousauthentication.domain.use_case.UseCases
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {
    init {
        getAuthState()
    }

    var signInResponse by mutableStateOf<SignInResponse>(Success(false))
        private set
    var signOutResponse by mutableStateOf<SignOutResponse>(Success(false))
        private set

    fun getAuthState() = useCases.getAuthState(viewModelScope)

    fun signIn() = viewModelScope.launch {
        signInResponse = Loading
        signInResponse = useCases.signIn()
    }

    fun signOut() = viewModelScope.launch {
        signOutResponse = Loading
        signOutResponse = useCases.signOut()
    }
}