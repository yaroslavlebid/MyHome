package yaroslavlebid.apps.myhome.data.apartment

data class Price(
    val currency: Currency = Currency.USD,
    val amountOfMoney: Int = 0
)

enum class Currency {
    USD,
    EUR,
    UAH
}
