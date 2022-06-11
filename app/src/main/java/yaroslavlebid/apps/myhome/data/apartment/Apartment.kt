package yaroslavlebid.apps.myhome.data.apartment

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
import yaroslavlebid.apps.myhome.data.User

@Parcelize
data class Apartment(
    @DocumentId
    val id: String = "",

    val title: String = "",
    val description: String = "",
    val photos: List<Photo> = listOf(),
    val coverPhotoId: Int = 0,
    val location: ApartmentLocation = ApartmentLocation(),
    val author: User = User(),
    val typeOfApartment: ApartmentType = ApartmentType.PRIVATE_HOUSE,
    val advantages: List<ApartmentAdvantage> = listOf(),
    val ratingAvg: Float = 0f,
    val publicationTimestamp: Long = 0L,
    val minRoomPrice: Price = Price(),
    val beds: List<Bed> = emptyList()
    //val reviews: List<Review> = listOf(), --> saved in firebase doc, as collection
    //val rooms: List<Room> --> saved in firebase doc, as collection
) : Parcelable {
    // todo: move to repo or some manager, beacouse reviews will be saved in firebase document as collection
    /*val rating: Float
        get() {
            var sum = 0f
            reviews.forEach { sum += it.ratingAvg }
            return sum/reviews.size
        }*/
    val peopleCapacity: Int
        get() {
            var sum = 0
            beds.forEach { when (it.type) {
                TypeOfBed.SINGLE -> sum += 1
                TypeOfBed.DOUBLE, TypeOfBed.BUNK -> sum +=2 }
            }
            return sum
        }
}
