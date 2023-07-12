package yaroslavlebid.apps.myhome.ui.home.apartments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.repository.ApartmentRepository
import yaroslavlebid.apps.myhome.repository.FavoriteRepository
import yaroslavlebid.apps.myhome.utils.FreshLiveData
import yaroslavlebid.apps.myhome.utils.toApartment

class ApartmentListViewModel(
    private val apartmentRepository: ApartmentRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _apartments = FreshLiveData<List<Apartment>>()
    val apartments: LiveData<List<Apartment>> = _apartments

    fun requestApartments() {
        _isLoading.value = true
        apartmentRepository.getApartmentList()
            .addOnSuccessListener { snapshot ->
                val apartmentList = mutableListOf<Apartment>()
                for (document in snapshot) {
                    try {
                        val apartment = document.toApartment()
                        apartmentList.add(apartment)
                    } catch (error: Throwable) {
                        Timber.e("Can't parse document to object!", error)
                    }
                }

                _apartments.value = apartmentList
            }
            .addOnFailureListener {
                // todo: it will not work, if user don't have internet connection
                Timber.e("Error, when trying to retreive data from remote db", it)
            }
            .addOnCompleteListener {
                _isLoading.value = false
            }
    }

    fun doSearch(query: String) {
        _isLoading.value = true
        apartmentRepository.getApartmentList()
            .addOnSuccessListener { snapshot ->
                val apartmentList = mutableListOf<Apartment>()
                for (document in snapshot) {
                    try {
                        val apartment = document.toApartment()
                        apartmentList.add(apartment)
                    } catch (error: Throwable) {
                        Timber.e("Can't parse document to object!", error)
                    }
                }

                _apartments.value = apartmentList
            }
            .addOnFailureListener {
                // todo: it will not work, if user don't have internet connection
                Timber.e("Error, when trying to retreive data from remote db", it)
            }
            .addOnCompleteListener {
                _isLoading.value = false
            }
    }

    fun addApartmentToFavorite(apartment: Apartment) {
        favoriteRepository.addApartment(apartment).addOnSuccessListener {
            Timber.d("Apartment $apartment added to favorite")
        }.addOnFailureListener {
            Timber.e("Can't add apartment $apartment to favorites")
        }
    }

    fun sortByPrice(apartments: MutableList<Apartment>) {
        _isLoading.value = true
        _apartments.value = apartments.sortedBy { it.minRoomPrice.amountOfMoney }
        _isLoading.value = false
    }

    fun sortByRating(apartments: MutableList<Apartment>) {
        _isLoading.value = true
        _apartments.value = apartments.sortedBy { it.ratingAvg }
        _isLoading.value = false
    }

    fun removeApartmentFromFavorite(apartment: Apartment) {
        favoriteRepository.removeApartment(apartment.id).addOnSuccessListener {
            Timber.d("Apartment $apartment removed from favorites")
        }.addOnFailureListener {
            Timber.e("Can't remove $apartment from favorites")
        }
    }

    fun filterApartments(apartmentList: MutableList<Apartment>, string: String) {
        _isLoading.value = true
        _apartments.value = apartmentList.filter { it.location.city == string }
        _isLoading.value = false
    }
}