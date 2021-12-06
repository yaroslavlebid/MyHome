package yaroslavlebid.apps.myhome.ui.login.ui.login.sign_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.repository.AuthRepository

class SignUpViewModel(val authRepository: AuthRepository) : ViewModel() {

    private val _registrationStatus = MutableLiveData<RegistrationEvent>()
    val registrationStatus: LiveData<RegistrationEvent> = _registrationStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(email: String, password: String) {
        _isLoading.value = true
        authRepository.registerUser(email, password)
            .addOnCompleteListener {
                _isLoading.value = false
            }
            .addOnSuccessListener {
                _registrationStatus.value = RegistrationEvent(RegistrationStatus.SUCCESS)
            }
            .addOnFailureListener { error ->
                Timber.d("Registration failed: $error")
                var event: RegistrationEvent
                if (error.message == null) {
                    event = RegistrationEvent(
                        RegistrationStatus.ERROR_DEFAULT,
                        RegistrationStatusMap.getErrorMessage(RegistrationStatus.ERROR_DEFAULT)
                    )
                } else {
                    event = RegistrationEvent(RegistrationStatus.CUSTOM_ERROR, error.message.toString())
                }
                _registrationStatus.value = event
            }
    }
}