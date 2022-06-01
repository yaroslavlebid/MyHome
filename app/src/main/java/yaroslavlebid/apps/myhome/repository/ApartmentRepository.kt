package yaroslavlebid.apps.myhome.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.data.apartment.ApartmentLocation
import yaroslavlebid.apps.myhome.data.apartment.Photo
import yaroslavlebid.apps.myhome.data.apartment.Price
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTION_APARTMENTS

interface ApartmentRepository {
    fun getApartmentList(): Task<QuerySnapshot>

    fun addMockApartmentsToDb()
}

class ApartmentRepositoryImpl(private val db: FirebaseFirestore) : ApartmentRepository {
    override fun getApartmentList() = db.collection(COLLECTION_APARTMENTS).get()

    //fixme: for test
    override fun addMockApartmentsToDb() {
        val apartment1 = Apartment(
            title = "Lviv Hotel",
            description = "This hotel is located in the heart of Lviv...",
            photos = listOf(Photo(mediumImageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/259260775.jpg?k=120d29e1485e411dcaf89a7e239350a0d2b66ed5872a395cf16c8d22cb100775&o=&hp=1")),
            coverPhotoId = 0,
            location = ApartmentLocation(city = "Lviv", street = "Vyacheslava Chornovola str.", numberOfHouse = "7"),
            ratingAvg = 4.1f,
            publicationTimestamp = System.currentTimeMillis(),
            minRoomPrice = Price()
        )

        val apartment2 = Apartment(
            title = "Sonata Hotel",
            description = "Wonderful place for relax with...",
            photos = listOf(Photo(mediumImageUrl = "https://sonatahotel.com.ua/wp-content/uploads/2019/07/%D1%84%D0%B0%D1%81%D0%B0%D0%B4-11-815x500.jpg")),
            coverPhotoId = 0,
            location = ApartmentLocation(city = "Lviv", street = "Somewhere str.", numberOfHouse = "52"),
            ratingAvg = 4.5f,
            publicationTimestamp = System.currentTimeMillis()
        )

        val apartment3 = Apartment(
            title = "Grand Hotel Lviv",
            description = "This hotel is located in the heart of Lviv...",
            photos = listOf(Photo(mediumImageUrl = "https://iameco.com.ua/wp-content/uploads/2019/08/136312738.jpg")),
            coverPhotoId = 0,
            location = ApartmentLocation(city = "Lviv", street = "Central str..", numberOfHouse = "15А"),
            ratingAvg = 4.9f,
            publicationTimestamp = System.currentTimeMillis()
        )

        db.collection(COLLECTION_APARTMENTS).add(apartment1)
        db.collection(COLLECTION_APARTMENTS).add(apartment2)
        db.collection(COLLECTION_APARTMENTS).add(apartment3)
    }
}
