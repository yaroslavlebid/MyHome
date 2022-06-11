package yaroslavlebid.apps.myhome.data.apartment

import android.os.Parcelable
import java.util.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val name: String,
    val beds: List<Bed>,
    val photo: Photo,
    val price: Price = Price(),
    val bookedDates: List<String> = listOf()
) : Parcelable {
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

@Parcelize
data class Bed(val type: TypeOfBed = TypeOfBed.SINGLE) : Parcelable

enum class TypeOfBed {
    SINGLE,
    DOUBLE,
    BUNK
}
