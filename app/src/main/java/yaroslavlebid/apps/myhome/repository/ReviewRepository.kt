package yaroslavlebid.apps.myhome.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTION_APARTMENTS
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTION_REVIEWS
import yaroslavlebid.apps.myhome.repository.firestore.COLLECTION_SAVED_APARTMENTS

interface ReviewRepository {
    fun getReviewsForApartment(apartmentId: String): Task<QuerySnapshot>
}

class ReviewRepositoryImpl(private val db: FirebaseFirestore): ReviewRepository {
    override fun getReviewsForApartment(apartmentId: String) = db.collection(COLLECTION_APARTMENTS).document(apartmentId).collection(
        COLLECTION_REVIEWS).get()
}