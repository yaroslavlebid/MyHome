package yaroslavlebid.apps.myhome.ui.login.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.data.User
import yaroslavlebid.apps.myhome.repository.AuthRepository
import yaroslavlebid.apps.myhome.repository.UserRepository
import yaroslavlebid.apps.myhome.ui.helpers.Event

class SignUpViewModel(
    val authRepository: AuthRepository,
    val userRepository: UserRepository
) : ViewModel() {

    private val _registrationStatus = MutableLiveData<Event<RegistrationStatus>>()
    val registrationStatus: LiveData<Event<RegistrationStatus>> = _registrationStatus

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
                        val newUser = User(
                            id = firebaseUser.uid,
                            email = email,
                            registrationTimestamp = System.currentTimeMillis()
                        )
                        userRepository.addUserToDb(newUser)
                        _registrationStatus.value =
                            Event<RegistrationStatus>(RegistrationStatus.SUCCESS)
                        Timber.d("Success register user with id: ${firebaseUser.uid}}")
                        return@addOnSuccessListener
                    }.run {
                        _registrationStatus.value = Event<RegistrationStatus>(
                            RegistrationStatus.ERROR_DEFAULT,
                            RegistrationStatusMap.getErrorMessage(RegistrationStatus.ERROR_DEFAULT)
                        )
                    }
                }
                .addOnFailureListener { error ->
                    Timber.d("Registration failed: $error")
                    var event: Event<RegistrationStatus>
                    if (error.message == null) {
                        event = Event<RegistrationStatus>(RegistrationStatus.ERROR_DEFAULT)
                    } else {
                        event = Event<RegistrationStatus>(
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
                Event<RegistrationStatus>(status)
            return false
        }

        if (password.isEmpty()) {
            Timber.d("Password is empty")
            val status = RegistrationStatus.ERROR_PASSWORD_EMPTY
            _registrationStatus.value =
                Event<RegistrationStatus>(status)
            return false
        }

        if (password.length < 6) {
            Timber.d("Password too short")
            val status = RegistrationStatus.ERROR_PASSWORD_TOO_SHORT
            _registrationStatus.value =
                Event<RegistrationStatus>(status)
            return false
        }

        return true
    }
}