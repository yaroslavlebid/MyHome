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
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTIONS_ROOMS
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTION_APARTMENTS
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTION_REVIEWS

interface ApartmentRepository {
    fun getApartmentList(): Task<QuerySnapshot>

    fun getApartmentById(id: String): Task<DocumentSnapshot>

    fun addApartmentToDb(apartment: Apartment): Task<Void>

    fun addMockApartmentsToDb()

    fun getRoomList(apartmentId: String): Task<QuerySnapshot>
}

class ApartmentRepositoryImpl(private val db: FirebaseFirestore) : ApartmentRepository {
    override fun getApartmentList() = db.collection(COLLECTION_APARTMENTS).get()

    override fun addApartmentToDb(apartment: Apartment) =
        db.collection(COLLECTION_APARTMENTS).document(apartment.id).set(apartment)

    override fun getApartmentById(id: String): Task<DocumentSnapshot> =
        db.collection(COLLECTION_APARTMENTS).document(id).get()

    override fun getRoomList(apartmentId: String): Task<QuerySnapshot> =
        db.collection(COLLECTION_APARTMENTS).document(apartmentId).collection(
            COLLECTIONS_ROOMS
        ).get()


    //fixme: for test
    override fun addMockApartmentsToDb() {
        val apartments = getMockApartments()
        // 1-3 lviv, 3-6 other
        db.collection(COLLECTION_APARTMENTS).document(apartments[0].id).set(apartments[0])

        db.collection(COLLECTION_APARTMENTS).document(apartments[5].id).set(apartments[5])

        db.collection(COLLECTION_APARTMENTS).document(apartments[1].id).set(apartments[1])

        db.collection(COLLECTION_APARTMENTS).document(apartments[4].id).set(apartments[4])

        db.collection(COLLECTION_APARTMENTS).document(apartments[2].id).set(apartments[2])

        db.collection(COLLECTION_APARTMENTS).document(apartments[3].id).set(apartments[3])


        val reviews = getMockReviews()
        db.collection(COLLECTION_APARTMENTS).document().collection(
            COLLECTION_REVIEWS
        ).add(reviews[0])
        db.collection(COLLECTION_APARTMENTS).document(apartments[0].id).collection(
            COLLECTION_REVIEWS
        ).add(reviews[1])

        db.collection(COLLECTION_APARTMENTS).document(apartments[1].id).collection(
            COLLECTION_REVIEWS
        ).add(reviews[0])
        db.collection(COLLECTION_APARTMENTS).document(apartments[1].id).collection(
            COLLECTION_REVIEWS
        ).add(reviews[1])

        db.collection(COLLECTION_APARTMENTS).document(apartments[2].id).collection(
            COLLECTION_REVIEWS
        ).add(reviews[0])
        db.collection(COLLECTION_APARTMENTS).document(apartments[2].id).collection(
            COLLECTION_REVIEWS
        ).add(reviews[1])

        val rooms = getMockRooms()
        db.collection(COLLECTION_APARTMENTS).document(apartments[1].id).collection(COLLECTIONS_ROOMS)
            .add(rooms[0])
        db.collection(COLLECTION_APARTMENTS).document(apartments[1].id).collection(COLLECTIONS_ROOMS)
            .add(rooms[1])
        /*db.collection(COLLECTION_APARTMENTS).document("2d656942-d9d0-4dc5-901b-1de3350e3849").collection(
            COLLECTION_REVIEWS
        ).add(myReview())*/
    }

    private fun getMockApartments(): List<Apartment> {
        val apartment1 = Apartment(
            id = "1",
            title = "Затишна квартира",
            description = "Гарна, затишна, однокімнатна квартира. Стильний ремонт. Вся техніка. У квартирі можуть проживати дві людини. Квартира розташована в історичному центрі Львова, до Оперного театру 10-12 хвилин пішки, біля ТЦ «Форум».",
            photos = listOf(
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/pro_photo_tool/Hosting-38170315-unapproved/original/aecfa72b-6f7c-4a0d-8ab6-a5f3d3a1f26d.JPEG?im_w=1200"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/pro_photo_tool/Hosting-38170315-unapproved/original/e9043bc3-51b3-4791-b20a-efeca0dabb6e.JPEG?im_w=1440"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/pro_photo_tool/Hosting-38170315-unapproved/original/8d485a49-0ef3-4737-bd42-579a7a481505.JPEG?im_w=1440")
            ),
            coverPhotoId = 0,
            location = ApartmentLocation(
                country = "Україна",
                city = "Львів",
                street = "вул. Чорновола",
                "42A",
                longitude = 49.852005729072104,
                latitude = 24.016833504604524
            ),
            author = User(
                firstName = "Ольга",
                photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/makeup.jpg"
            ),
            typeOfApartment = ApartmentType.FLAT,
            advantages = listOf(
                ApartmentAdvantage("Безкоштовний Wi-Fi"),
                ApartmentAdvantage("Безкоштовна парковка"),
                ApartmentAdvantage("Прибирання"),
                ApartmentAdvantage("Домашні тварини дозволені")
            ),
            ratingAvg = 4.3f,
            publicationTimestamp = 0L,
            minRoomPrice = Price(Currency.UAH, 690),
            listOf(Bed(type = TypeOfBed.DOUBLE))
        )

        val apartment2 = Apartment(
            id = "4",
            title = "Grand Hotel Lviv",
            description = "Готель «Гранд» розташований в історичному центрі Львова і поєднує сучасний комфорт найвищого рівня та вишукану класичну атмосферу. На всій території готелю надається безкоштовний Wi-Fi.",
            photos = listOf(
                Photo(mediumImageUrl = "https://t-cf.bstatic.com/xdata/images/hotel/max1280x900/259260775.jpg?k=120d29e1485e411dcaf89a7e239350a0d2b66ed5872a395cf16c8d22cb100775&o=&hp=1"),
                Photo(mediumImageUrl = "https://t-cf.bstatic.com/xdata/images/hotel/max1280x900/308925385.jpg?k=70fc5a7e6a8652729ba6bbe66df56dbb610505514c4d63b7a42f1673b0475a35&o=&hp=1"),
                Photo(mediumImageUrl = "https://t-cf.bstatic.com/xdata/images/hotel/max1280x900/240410330.jpg?k=b172352faae5203a8c86bd2ae6746049c210b91cfa9cde44b59dd92e9cf83207&o=&hp=1")
            ),
            coverPhotoId = 0,
            location = ApartmentLocation(
                country = "Україна",
                city = "Львів",
                street = "проспект Свободи",
                "13",
                longitude = 49.84086086686511,
                latitude = 24.027353998145102
            ),
            author = User(
                firstName = "Андрій",
                photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/sex.jpg"
            ),
            typeOfApartment = ApartmentType.HOTEL,
            advantages = listOf(
                ApartmentAdvantage("Безкоштовний Wi-Fi"),
                ApartmentAdvantage("Безкоштовна парковка"),
                ApartmentAdvantage("Прибирання"),
                ApartmentAdvantage("Бар"),
                ApartmentAdvantage("Ресторан"),
                ApartmentAdvantage("Сніданок")
            ),
            ratingAvg = 4.9f,
            publicationTimestamp = 0L,
            minRoomPrice = Price(Currency.UAH, 4500),
        )

        val apartment3 = Apartment(
            id = "5",
            title = "Квартира у центрі Львова",
            description = "Гарна квартира в центрі міста, 1 хвилина ходьби до площі Ринок. Вам сподобається наше місце!",
            photos = listOf(
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/44781710/30652268_original.jpg?im_w=1200"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/44781836/811249a8_original.jpg?im_w=1440"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/44781814/00299502_original.jpg?im_w=1440")
            ),
            coverPhotoId = 0,
            location = ApartmentLocation(
                country = "Україна",
                city = "Львів",
                street = "площа Ринок",
                "12",
                longitude = 49.84149742575578,
                latitude = 24.03254675474058
            ),
            author = User(
                firstName = "Олена",
                photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/emotion.jpg"
            ),
            typeOfApartment = ApartmentType.FLAT,
            advantages = listOf(
                ApartmentAdvantage("Безкоштовний Wi-Fi"),
                ApartmentAdvantage("Безкоштовна парковка"),
                ApartmentAdvantage("Прибирання"),
                ApartmentAdvantage("Сніданок")
            ),
            ratingAvg = 4.4f,
            publicationTimestamp = 0L,
            minRoomPrice = Price(Currency.UAH, 1150),
            listOf(Bed(type = TypeOfBed.DOUBLE))


        )

        val apartment4 = Apartment(
            id = "2",
            title = "Квартира з чудовим видом",
            description = "Затишна квартира в центрі Троїцького куточка на вулиці Екатеринінській. Ідеальне розташування, 10 хвилин ходьби до Дерибасовської, Оперного театру, за кутом дуже гарний ресторан Tavernetta. Залізнична станція знаходиться за 10 хвилин ходьби.",
            photos = listOf(
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/25fe4d6a-ac41-493e-8b44-d5fa9be2b409.jpg?im_w=1200"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/d13319b7-f910-4458-b81a-e75a585071f9.jpg?im_w=1440"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/0ebe42c2-b677-4386-ac87-55f9267f3c19.jpg?im_w=1440")
            ),
            coverPhotoId = 0,
            location = ApartmentLocation(
                country = "Україна",
                city = "Одеса",
                street = "вул. Катеринська",
                "15",
                longitude = 0.0,
                latitude = 0.0
            ),
            author = User(
                firstName = "Олена",
                photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/emotion.jpg"
            ),
            typeOfApartment = ApartmentType.FLAT,
            advantages = listOf(
                ApartmentAdvantage("Безкоштовний Wi-Fi"),
                ApartmentAdvantage("Безкоштовна парковка"),
                ApartmentAdvantage("Прибирання"),
                ApartmentAdvantage("Сніданок")
            ),
            ratingAvg = 3.9f,
            publicationTimestamp = 0L,
            minRoomPrice = Price(Currency.UAH, 1200),
            listOf(Bed(type = TypeOfBed.DOUBLE))


        )

        val apartment5 = Apartment(
            id = "3",
            title = "Просторна квартира",
            description = "Гарна квартира в центрі міста, 1 хвилина ходьби до площі Ринок. Вам сподобається наше місце!",
            photos = listOf(
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/88a26dec-e27b-4e7d-8c89-71cf699998a1.jpg?im_w=1200"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/0ad722fc-f108-4b34-b0b2-1348696f62a4.jpg?im_w=1440"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/72748622-2528-4261-9dda-b58689f074f4.jpg?im_w=1440")
            ),
            coverPhotoId = 0,
            location = ApartmentLocation(
                country = "Україна",
                city = "Київ",
                street = "вул. Катеринська",
                "15",
                longitude = 0.0,
                latitude = 0.0
            ),
            author = User(
                firstName = "Олена",
                photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/emotion.jpg"
            ),
            typeOfApartment = ApartmentType.FLAT,
            advantages = listOf(
                ApartmentAdvantage("Безкоштовний Wi-Fi"),
                ApartmentAdvantage("Безкоштовна парковка"),
                ApartmentAdvantage("Прибирання"),
                ApartmentAdvantage("Сніданок")
            ),
            ratingAvg = 4.3f,
            publicationTimestamp = 0L,
            minRoomPrice = Price(Currency.UAH, 1870),
            listOf(Bed(type = TypeOfBed.DOUBLE))


        )

        val apartment6 = Apartment(
            id = "6",
            title = "Парковий готель",
            description = "Чотиризірковий готель \"Парк\" розташований у самому серці Харкова. Гостям пропонують просторі звуконепроникні номери з безкоштовним бездротовим доступом до Інтернету (Wi-Fi). За 12 хвилин ходьби від готелю розміщена станція метро «Київська», звідки можна дістатися до основних туристичних пам'яток міста.",
            photos = listOf(
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/25fe4d6a-ac41-493e-8b44-d5fa9be2b409.jpg?im_w=1200"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/d13319b7-f910-4458-b81a-e75a585071f9.jpg?im_w=1440"),
                Photo(mediumImageUrl = "https://a0.muscache.com/im/pictures/0ebe42c2-b677-4386-ac87-55f9267f3c19.jpg?im_w=1440")
            ),
            coverPhotoId = 0,
            location = ApartmentLocation(
                country = "Україна",
                city = "Харків",
                street = "вул. Шевченка",
                "79",
                longitude = 0.0,
                latitude = 0.0
            ),
            author = User(
                firstName = "Олена",
                photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/emotion.jpg"
            ),
            typeOfApartment = ApartmentType.FLAT,
            advantages = listOf(
                ApartmentAdvantage("Безкоштовний Wi-Fi"),
                ApartmentAdvantage("Безкоштовна парковка"),
                ApartmentAdvantage("Прибирання"),
                ApartmentAdvantage("Сніданок")
            ),
            ratingAvg = 4.9f,
            publicationTimestamp = 0L,
            minRoomPrice = Price(Currency.UAH, 1999),
            listOf(Bed(type = TypeOfBed.DOUBLE))


        )

        return listOf(apartment1, apartment2, apartment3, apartment4, apartment5, apartment6)
    }

    private fun getMockReviews(): List<Review> {
        val review1 = Review(
            id = "1",
            author = User(
                firstName = "Олена",
                photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/emotion.jpg"
            ),
            comment = "Рекомендую! Все було чудово!",
            rating = listOf(Rating(rating = 5)),
            likes = 119
        )
        val review2 = Review(
            id = "1",
            author = User(
                firstName = "Андрій",
                photoUrl = "https://static.generated.photos/vue-static/face-generator/landing/demo-previews/sex.jpg"
            ),
            comment = "Дякую вам! Помешкання надзвичайно гарне, ідеальна чистота.",
            rating = listOf(Rating(rating = 4)),
            likes = 119
        )
        return listOf(review1, review2)
    }

    private fun myReview() = Review(
        id = "1",
        author = User(
            firstName = "Ярослав",
            photoUrl = "https://firebasestorage.googleapis.com/v0/b/myhome-9085a.appspot.com/o/profile_images%2F3b481b39-a8d7-466c-8577-70efbb1d3945?alt=media&token=7fca0a74-f031-49ce-a0f1-0a68cfd11141"
        ),
        comment = "Дякую, все чудово!",
        rating = listOf(Rating(rating = 5)),
        likes = 0
    )

    private fun getMockRooms(): List<Room> {
        val room1 = Room(
            name = "Стандартна кімната з двоспальним ліжком",
            beds = listOf(Bed(type = TypeOfBed.DOUBLE)),
            photo = Photo(mediumImageUrl = "https://t-cf.bstatic.com/xdata/images/hotel/max1024x768/135623215.jpg?k=c3142c150f8cea113e411f5e6a49b79b10bbae2e5e6d8712d72da5548abfe101&o="),
            price = Price(currency = Currency.UAH, 4620)
        )

        val room2 = Room(
            name = "Presidential Suite",
            beds = listOf(Bed(type = TypeOfBed.DOUBLE), Bed(type = TypeOfBed.DOUBLE)),
            photo = Photo(mediumImageUrl = "https://t-cf.bstatic.com/xdata/images/hotel/max1024x768/193835997.jpg?k=f16e85ed2ae7993041016899915149510f7bf3376f04cc488b534ab7c8cbeb47&o="),
            price = Price(currency = Currency.USD, 55199)
        )
        return listOf(room1, room2)
    }
}
