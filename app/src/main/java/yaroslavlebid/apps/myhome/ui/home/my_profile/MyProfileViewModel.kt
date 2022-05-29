package yaroslavlebid.apps.myhome.ui.home.my_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import yaroslavlebid.apps.myhome.repository.AuthRepository

class MyProfileViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _isLogoutSuccess = MutableLiveData<Boolean>()
    val isLogoutSuccess: LiveData<Boolean> = _isLogoutSuccess

    fun doLogout() {
        authRepository.signOut()
        _isLogoutSuccess.value = true
    }
}