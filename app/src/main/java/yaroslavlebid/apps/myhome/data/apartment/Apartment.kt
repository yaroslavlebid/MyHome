package yaroslavlebid.apps.myhome.data.apartment

import com.google.firebase.firestore.DocumentId
import yaroslavlebid.apps.myhome.data.User
import yaroslavlebid.apps.myhome.data.review.Review

data class Apartment(
    @DocumentId
    val id: String = "",

    val title: String = "",
    val desription: String = "",
    val publicationTimestamp: Long = 0,
    val photos: List<ApartmentPhoto> = listOf(),
    val coverPhotoId: Int = 0,
    val location: ApartmentLocation = ApartmentLocation(),
    val author: User = User(),
    val typeOfApartment: ApartmentType = ApartmentType.PRIVATE_HOUSE,
    val advantages: List<ApartmentAdvantage> = listOf(),
    val ratingAvg: Float = 0f
    //val reviews: List<Review> = listOf(), --> saved in firebase doc, as collection
    //val rooms: List<Room> --> saved in firebase doc, as collection
) {
    // todo: move to repo or some manager, beacouse reviews will be saved in firebase document as collection
    /*val rating: Float
        get() {
            var sum = 0f
            reviews.forEach { sum += it.ratingAvg }
            return sum/reviews.size
        }*/
}
