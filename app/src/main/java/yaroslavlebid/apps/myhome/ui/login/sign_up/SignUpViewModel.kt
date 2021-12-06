package yaroslavlebid.apps.myhome.ui.login.sign_up

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

    private val _fillUserProfileStatus = MutableLiveData<RegistrationEvent>()
    val fillUserProfileStatus: LiveData<RegistrationEvent> = _fillUserProfileStatus

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
                        userRepository.addUserToDb(User(id = firebaseUser.uid, email = email))
                        _registrationStatus.value = RegistrationEvent(RegistrationStatus.SUCCESS)
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

    fun fillUserProfile(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUrl: String,
        dateOfBirth: String
    ) {
        val firebaseCurrentUser = authRepository.getCurrentFirebaseUser()
        if (firebaseCurrentUser != null) {
            _isLoading.value = true
            userRepository.getUserById(firebaseCurrentUser.uid)
                .addOnSuccessListener { dataSnapshot ->
                    var user = dataSnapshot.toObject(User::class.java)
                    if (user == null) {
                        _fillUserProfileStatus.value = RegistrationEvent(
                            RegistrationStatus.ERROR_DEFAULT,
                            RegistrationStatusMap.getErrorMessage(RegistrationStatus.ERROR_DEFAULT)
                        )
                        return@addOnSuccessListener
                    } else {
                        user.lastName = lastName
                        user.dateOfBirth = dateOfBirth
                        user.firstName = firstName
                        user.phoneNumber = phoneNumber
                        user.photoUrl = photoUrl
                        userRepository.updateUser(user).addOnSuccessListener {
                            Timber.d("User ${user.id} updated successful!")
                            _fillUserProfileStatus.value =
                                RegistrationEvent(RegistrationStatus.SUCCESS)
                        }.addOnFailureListener {
                            Timber.e("Error, can't update user profile")
                        }
                    }
                }.addOnFailureListener {
                    Timber.e("Error, when try to get user", it)
                }.addOnCompleteListener {
                    _isLoading.value = false
                }
        }

    }

    private fun isUserRegistrationDataValid(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            val status = RegistrationStatus.ERROR_EMAIL_EMPTY
            _registrationStatus.value =
                RegistrationEvent(status, RegistrationStatusMap.getErrorMessage(status))
            return false
        }

        if (password.isEmpty()) {
            val status = RegistrationStatus.ERROR_PASSWORD_EMPTY
            _registrationStatus.value =
                RegistrationEvent(status, RegistrationStatusMap.getErrorMessage(status))
            return false
        }

        if (password.length < 6) {
            val status = RegistrationStatus.ERROR_PASSWORD_TOO_SHORT
            _registrationStatus.value =
                RegistrationEvent(status, RegistrationStatusMap.getErrorMessage(status))
            return false
        }

        return true
    }
}