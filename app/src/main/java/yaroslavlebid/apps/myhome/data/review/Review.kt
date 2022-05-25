package yaroslavlebid.apps.myhome.data.review

data class Review(
    val title: String = "",
    val description: String = "",
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
