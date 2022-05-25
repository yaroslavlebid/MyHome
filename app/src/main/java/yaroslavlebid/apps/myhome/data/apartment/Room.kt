package yaroslavlebid.apps.myhome.data.apartment

import java.util.*

data class Room(
    val name: String,
    val beds: List<Bed>,
    val photo: Photo,
    val price: Price = Price(),
    val bookedDates: List<String> = listOf()
)
{
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

data class Bed(val type: TypeOfBed)

enum class TypeOfBed {
    SINGLE,
    DOUBLE,
    BUNK
}
