package yaroslavlebid.apps.myhome.data.review

data class Rating(
    val rating: Int = 0,
    val category: RatingCategory = RatingCategory.SERVICE
)
