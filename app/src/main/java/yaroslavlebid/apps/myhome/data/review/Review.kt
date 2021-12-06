package yaroslavlebid.apps.myhome.data.review

data class Review(
    val title: String = "",
    val description: String = "",
    val rating: List<Rating> = listOf()
)
