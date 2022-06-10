package yaroslavlebid.apps.myhome.data.review

import com.google.firebase.firestore.DocumentId
import yaroslavlebid.apps.myhome.data.User

data class Review(
    @DocumentId
    val id: String = "",

    val author: User = User(),
    val comment: String = "",
    val rating: List<Rating> = listOf(),
    val likes: Int = 0
) {
    val ratingAvg: Float
        get() {
            var sum = 0f
            rating.forEach { sum += it.rating }
            return sum/rating.size
        }
}
