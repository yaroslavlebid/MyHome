package yaroslavlebid.apps.myhome.ui.login.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.repository.AuthRepository
import yaroslavlebid.apps.myhome.ui.helpers.Event

class SignInViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginStatus = MutableLiveData<Event<LoginStatus>>()
    val loginStatus: LiveData<Event<LoginStatus>> = _loginStatus

    fun signIn(email: String, password: String) {
        if (isUserLoginDataValid(email, password)) {
            _isLoading.value = true
            authRepository.signIn(email, password)
                .addOnCompleteListener { _isLoading.value = false }
                .addOnSuccessListener {
                    Timber.d("Success login")
                    _loginStatus.value = Event<LoginStatus>(LoginStatus.SUCCESS)
                }
                .addOnFailureListener { error ->
                    Timber.d("Failure login")
                    if (error.message.isNullOrEmpty()) {
                        _loginStatus.value = Event<LoginStatus>(LoginStatus.ERROR_DEFAULT)
                    } else {
                        _loginStatus.value = Event<LoginStatus>(LoginStatus.CUSTOM_ERROR, error.message.toString())
                    }
                }
        }
    }

    private fun isUserLoginDataValid(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            Timber.d("Email is empty")
            val status = LoginStatus.ERROR_EMAIL_EMPTY
            _loginStatus.value =
                Event<LoginStatus>(status)
            return false
        }

        if (password.isEmpty()) {
            Timber.d("Password is empty")
            val status = LoginStatus.ERROR_PASSWORD_EMPTY
            _loginStatus.value =
                Event<LoginStatus>(status)
            return false
        }

        if (password.length < 6) {
            Timber.d("Password too short")
            val status = LoginStatus.ERROR_PASSWORD_TOO_SHORT
            _loginStatus.value =
                Event<LoginStatus>(status)
            return false
        }

        return true
    }
}