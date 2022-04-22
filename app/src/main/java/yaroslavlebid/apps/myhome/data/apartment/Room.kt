package yaroslavlebid.apps.myhome.data.apartment

import java.util.*

data class Room(
    val description: String = "",
    val beds: List<Bed>,
    val price: Price = Price(),
    val square: Int = 0,
    val numberOfRooms: Int = 0,
    val bookedDates: List<String> = listOf()
)
{
    val amountOfBed: Int
        get() = beds.size
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
