package yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import yaroslavlebid.apps.myhome.data.apartment.Room
import yaroslavlebid.apps.myhome.repository.ApartmentRepository
import yaroslavlebid.apps.myhome.utils.toRoom

class RoomListViewModel(private val apartmentRepository: ApartmentRepository) : ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> = _rooms

    fun loadRooms(apartmentId: String) {
        apartmentRepository.getRoomList(apartmentId).addOnSuccessListener { query ->
            val rooms = mutableListOf<Room>()
            query.forEach {
                rooms.add(it.toRoom())
            }
            _rooms.value = rooms
        }
    }
}