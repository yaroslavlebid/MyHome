package yaroslavlebid.apps.myhome.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import yaroslavlebid.apps.myhome.data.User
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.data.apartment.ApartmentAdvantage
import yaroslavlebid.apps.myhome.data.apartment.ApartmentLocation
import yaroslavlebid.apps.myhome.data.apartment.ApartmentType
import yaroslavlebid.apps.myhome.data.apartment.Bed
import yaroslavlebid.apps.myhome.data.apartment.Currency
import yaroslavlebid.apps.myhome.data.apartment.Photo
import yaroslavlebid.apps.myhome.data.apartment.Price
import yaroslavlebid.apps.myhome.data.apartment.Room
import yaroslavlebid.apps.myhome.data.apartment.TypeOfBed
import yaroslavlebid.apps.myhome.data.review.Rating
import yaroslavlebid.apps.myhome.data.review.Review
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTION_APARTMENTS
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTION_REVIEWS

interface ApartmentRepository {
    fun getApartmentList(): Task<QuerySnapshot>

    fun getApartmentById(id: String): Task<DocumentSnapshot>

    fun addApartmentToDb(apartment: Apartment): Task<Void>

    fun addMockApartmentsToDb()
}

class ApartmentRepositoryImpl(private val db: FirebaseFirestore) : ApartmentRepository {
    override fun getApartmentList() = db.collection(COLLECTION_APARTMENTS).get()

    override fun addApartmentToDb(apartment: Apartment) = db.collection(COLLECTION_APARTMENTS).document(apartment.id).set(apartment)

    override fun getApartmentById(id: String): Task<DocumentSnapshot> = db.collection(COLLECTION_APARTMENTS).document(id).get()




    //fixme: for test
    override fun addMockApartmentsToDb() {
        val apartments = getMockApartments()
        db.collection(COLLECTION_APARTMENTS).document(apartments[0].id).set(apartments[0])
        db.collection(COLLECTION_APARTMENTS).document(apartments[1].id).set(apartments[1])
        db.collection(COLLECTION_APARTMENTS).document(apartments[2].id).set(apartments[2])

        val reviews = getMockReviews()
        db.collection(COLLECTION_APARTMENTS).document().collection(
            COLLECTION_REVIEWS).add(reviews[0])
        db.collection(COLLECTION_APARTMENTS).document(apartments[0].id).collection(
            COLLECTION_REVIEWS).add(reviews[1])

        db.collection(COLLECTION_APARTMENTS).document(apartments[1].id).collection(
            COLLECTION_REVIEWS).add(reviews[0])
        db.collection(COLLECTION_APARTMENTS).document(apartments[1].id).collection(
            COLLECTION_REVIEWS).add(reviews[1])

        db.collection(COLLECTION_APARTMENTS).document(apartments[2].id).collection(
            COLLECTION_REVIEWS).add(reviews[0])
        db.collection(COLLECTION_APARTMENTS).document(apartments[2].id).collection(
            COLLECTION_REVIEWS).add(reviews[1])

        val rooms = getMockRooms()
        db.collection(COLLECTION_APARTMENTS).document(apartments[1].id).collection("rooms").add(rooms[0])
        db.collection(COLLECTION_APARTMENTS).document(apartments[1].id).collection("rooms").add(rooms[1])
    }

    private fun getMockApartments(): List<Apartment> {
        val apartment1 = Apartment(
            id = "1",
            title = "Apartment in Lviv",
            description = "Beautiful, cozy, studio apartment. Stylish renovation. All appliances. The apartment can accommodate two people. The apartment is located in the historic center of Lviv, to the Opera House 10-12 minutes walk, near the shopping center 'Forum'",
            photos = listOf(
                    Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/pro_photo_tool/Hosting-38170315-unapproved/original/aecfa72b-6f7c-4a0d-8ab6-a5f3d3a1f26d.JPEG?im_w=1200"),
                    Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/pro_photo_tool/Hosting-38170315-unapproved/original/e9043bc3-51b3-4791-b20a-efeca0dabb6e.JPEG?im_w=1440"),
                    Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/pro_photo_tool/Hosting-38170315-unapproved/original/8d485a49-0ef3-4737-bd42-579a7a481505.JPEG?im_w=1440")
            ),
            coverPhotoId = 0,
            location = ApartmentLocation(country = "Ukraine", city = "Lviv", street = "Chornovola st.", "42A", longitude = 49.852005729072104, latitude = 24.016833504604524),
            author = User(firstName = "Olga", photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/makeup.jpg"),
            typeOfApartment = ApartmentType.FLAT,
            advantages = listOf(ApartmentAdvantage("Free Wi-Fi"), ApartmentAdvantage("Free parking"), ApartmentAdvantage("Service"), ApartmentAdvantage("Pets allowed")),
            ratingAvg = 4.3f,
            publicationTimestamp = 0L,
            minRoomPrice = Price(Currency.USD, 23)
        )

        val apartment2 = Apartment(
            id = "2",
            title = "Grand Hotel Lviv",
            description = "The Grand Hotel is located in the historic center of Lviv and combines modern comfort of the highest level and a refined classic atmosphere. Free Wi-Fi is available throughout the hotel.",
            photos = listOf(
                Photo(mediumImageUrl = "https://t-cf.bstatic.com/xdata/images/hotel/max1280x900/259260775.jpg?k=120d29e1485e411dcaf89a7e239350a0d2b66ed5872a395cf16c8d22cb100775&o=&hp=1"),
                Photo(mediumImageUrl = "https://t-cf.bstatic.com/xdata/images/hotel/max1280x900/308925385.jpg?k=70fc5a7e6a8652729ba6bbe66df56dbb610505514c4d63b7a42f1673b0475a35&o=&hp=1"),
                Photo(mediumImageUrl = "https://t-cf.bstatic.com/xdata/images/hotel/max1280x900/240410330.jpg?k=b172352faae5203a8c86bd2ae6746049c210b91cfa9cde44b59dd92e9cf83207&o=&hp=1")
            ),
            coverPhotoId = 0,
            location = ApartmentLocation(country = "Ukraine", city = "Lviv", street = "Prospect Svoboty", "13", longitude = 49.84086086686511, latitude = 24.027353998145102),
            author = User(firstName = "Andriy", photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/sex.jpg"),
            typeOfApartment = ApartmentType.HOTEL,
            advantages = listOf(ApartmentAdvantage("Free Wi-Fi"), ApartmentAdvantage("Free parking"), ApartmentAdvantage("Service"), ApartmentAdvantage("Bar"), ApartmentAdvantage("Restaurant"), ApartmentAdvantage("Breakfest")),
            ratingAvg = 4.9f,
            publicationTimestamp = 0L,
            minRoomPrice = Price(Currency.USD, 150)
        )

        val apartment3 = Apartment(
            id = "3",
            title = "Apartment in Lviv city center",
            description = "Beautiful apartment in the city center, 1 minute walk to the Market Square. You will love our place!",
            photos = listOf(
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/44781710/30652268_original.jpg?im_w=1200"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/44781836/811249a8_original.jpg?im_w=1440"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/44781814/00299502_original.jpg?im_w=1440")
            ),
            coverPhotoId = 0,
            location = ApartmentLocation(country = "Ukraine", city = "Lviv", street = "Plosha Rynok", "12", longitude = 49.84149742575578, latitude = 24.03254675474058),
            author = User(firstName = "Olena", photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/emotion.jpg"),
            typeOfApartment = ApartmentType.HOTEL,
            advantages = listOf(ApartmentAdvantage("Free Wi-Fi"), ApartmentAdvantage("Free parking"), ApartmentAdvantage("Service"), ApartmentAdvantage("Breakfest")),
            ratingAvg = 3.9f,
            publicationTimestamp = 0L,
            minRoomPrice = Price(Currency.USD, 40)
        )

        return listOf(apartment1, apartment2, apartment3)
    }

    private fun getMockReviews(): List<Review> {
        val review1 = Review(
            id = "1",
            author = User(firstName = "Olena", photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/emotion.jpg"),
            comment = "I recommend! Everything was great!",
            rating = listOf(Rating(rating = 5)),
            likes = 119
        )
        val review2 = Review(
            id = "1",
            author = User(firstName = "Andriy", photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/sex.jpg"),
            comment = "Thank you! Everything was great!",
            rating = listOf(Rating(rating = 4)),
            likes = 119
        )
        return listOf(review1, review2)
    }

    private fun getMockRooms(): List<Room> {
        val room1 = Room(
            name = "Standard double room",
            beds = listOf(Bed(type = TypeOfBed.DOUBLE)),
            photo = Photo(mediumImageUrl = "https://t-cf.bstatic.com/xdata/images/hotel/max1024x768/135623215.jpg?k=c3142c150f8cea113e411f5e6a49b79b10bbae2e5e6d8712d72da5548abfe101&o="),
            price = Price(currency = Currency.USD, 154)
        )

        val room2 = Room(
            name = "Presidential Suite",
            beds = listOf(Bed(type = TypeOfBed.DOUBLE), Bed(type = TypeOfBed.DOUBLE)),
            photo = Photo(mediumImageUrl = "https://t-cf.bstatic.com/xdata/images/hotel/max1024x768/193835997.jpg?k=f16e85ed2ae7993041016899915149510f7bf3376f04cc488b534ab7c8cbeb47&o="),
            price = Price(currency = Currency.USD, 1866)
        )
        return listOf(room1, room2)
    }
}
