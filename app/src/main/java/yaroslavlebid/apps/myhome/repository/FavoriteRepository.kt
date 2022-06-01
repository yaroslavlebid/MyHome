package yaroslavlebid.apps.myhome.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.data.session.Session
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTION_SAVED_APARTMENTS
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTION_USERS

interface FavoriteRepository {
    fun getFavoriteApartments(): Task<QuerySnapshot>

    fun getFavoriteApartmentById(id: String): Task<DocumentSnapshot>

    fun addApartment(apartment: Apartment): Task<Void>

    fun removeApartment(id: String): Task<Void>
}

class FavoriteRepositoryImpl(private val db: FirebaseFirestore) : FavoriteRepository {
    override fun getFavoriteApartments(): Task<QuerySnapshot> = favoriteDb().get()

    override fun getFavoriteApartmentById(id: String) = favoriteDb().document(id).get()

    override fun addApartment(apartment: Apartment) = favoriteDb().document(apartment.id).set(apartment)

    override fun removeApartment(id: String) = favoriteDb().document(id).delete()

    private fun favoriteDb() = db.collection(COLLECTION_USERS).document(Session.userId).collection(
        COLLECTION_SAVED_APARTMENTS)

}