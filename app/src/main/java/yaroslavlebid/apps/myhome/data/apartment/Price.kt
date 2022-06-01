package yaroslavlebid.apps.myhome.data.apartment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Price(
    val currency: Currency = Currency.USD,
    val amountOfMoney: Int = 0
) : Parcelable

enum class Currency(val sign: String) {
    USD("$"),
    EUR("€"),
    UAH("₴")
}
