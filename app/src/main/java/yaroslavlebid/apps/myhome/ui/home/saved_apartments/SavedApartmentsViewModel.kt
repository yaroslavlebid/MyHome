package yaroslavlebid.apps.myhome.ui.home.saved_apartments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.repository.FavoriteRepository
import yaroslavlebid.apps.myhome.utils.FreshLiveData
import yaroslavlebid.apps.myhome.utils.handleParseError
import yaroslavlebid.apps.myhome.utils.toApartment

class SavedApartmentsViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val _apartments = MutableLiveData<List<Apartment>>()
    val apartments: LiveData<List<Apartment>> = _apartments

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _removedApartment = FreshLiveData<String>()
    val removedApartment: LiveData<String> = _removedApartment

    fun requestSavedApartments() {
        _isLoading.value = true
        favoriteRepository.getFavoriteApartments().addOnSuccessListener { snapshot ->
            handleParseError(_error) {
                val apartmentList = mutableListOf<Apartment>()
                for (document in snapshot) {
                    apartmentList.add(document.toApartment())
                }
                _apartments.value = apartmentList
            }
        }.addOnFailureListener {
            _error.value = it
            Timber.e(it, "Can't get saved apartments")
        }.addOnCompleteListener {
            _isLoading.value = false
        }
    }

    fun removeApartmentFromFavorites(id: String) {
        favoriteRepository.removeApartment(id).addOnSuccessListener {
            _removedApartment.value = id
        }.addOnFailureListener {
            Timber.e(it, "Can't remove apartment from favorites")
        }
    }
}