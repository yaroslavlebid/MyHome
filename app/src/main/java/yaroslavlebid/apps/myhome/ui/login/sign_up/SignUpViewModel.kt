package yaroslavlebid.apps.myhome.ui.login.sign_up

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.data.User
import yaroslavlebid.apps.myhome.repository.AuthRepository
import yaroslavlebid.apps.myhome.repository.UserRepository

class SignUpViewModel(
    val authRepository: AuthRepository,
    val userRepository: UserRepository
) : ViewModel() {

    private val _registrationStatus = MutableLiveData<RegistrationEvent>()
    val registrationStatus: LiveData<RegistrationEvent> = _registrationStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(email: String, password: String) {
        if (isUserRegistrationDataValid(email, password)) {
            _isLoading.value = true
            authRepository.registerUser(email, password)
                .addOnCompleteListener {
                    _isLoading.value = false
                }
                .addOnSuccessListener { result ->
                    result.user?.let { firebaseUser ->
                        userRepository.addUserToDb(
                            User(
                                id = firebaseUser.uid,
                                email = email,
                                registrationTimestamp = System.currentTimeMillis()
                            )
                        )
                        _registrationStatus.value = RegistrationEvent(RegistrationStatus.SUCCESS)
                        Timber.d("Success register user with id: ${firebaseUser.uid}}")
                        return@addOnSuccessListener
                    }.run {
                        _registrationStatus.value = RegistrationEvent(
                            RegistrationStatus.ERROR_DEFAULT,
                            RegistrationStatusMap.getErrorMessage(RegistrationStatus.ERROR_DEFAULT)
                        )
                    }
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
                        event = RegistrationEvent(
                            RegistrationStatus.CUSTOM_ERROR,
                            error.message.toString()
                        )
                    }
                    _registrationStatus.value = event
                }
        }
    }


    private fun isUserRegistrationDataValid(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            Timber.d("Email is empty")
            val status = RegistrationStatus.ERROR_EMAIL_EMPTY
            _registrationStatus.value =
                RegistrationEvent(status, RegistrationStatusMap.getErrorMessage(status))
            return false
        }

        if (password.isEmpty()) {
            Timber.d("Password is empty")
            val status = RegistrationStatus.ERROR_PASSWORD_EMPTY
            _registrationStatus.value =
                RegistrationEvent(status, RegistrationStatusMap.getErrorMessage(status))
            return false
        }

        if (password.length < 6) {
            Timber.d("Password too short")
            val status = RegistrationStatus.ERROR_PASSWORD_TOO_SHORT
            _registrationStatus.value =
                RegistrationEvent(status, RegistrationStatusMap.getErrorMessage(status))
            return false
        }

        return true
    }
}