package yaroslavlebid.apps.myhome.repository

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.File
import java.io.FileInputStream
import java.util.*

interface StorageRepository {
    fun uploadProfileImage(file: File): UploadTask
    fun uploadApartmentImage(file: File): UploadTask
}

private const val FOLDER_NAME_PROFILE_IMAGES = "/profile_images/"
private const val FOLDER_NAME_APARTMENTS_IMAGES = "/apartments_images/"

class StorageRepositoryImpl(private val firebaseStorage: FirebaseStorage) : StorageRepository {
    override fun uploadProfileImage(file: File) : UploadTask {
        val stream = FileInputStream(file)
        return firebaseStorage.reference
            .child(FOLDER_NAME_PROFILE_IMAGES)
            .child(UUID.randomUUID().toString())
            .putStream(stream)
    }

    override fun uploadApartmentImage(file: File): UploadTask {
        val stream = FileInputStream(file)
        return firebaseStorage.reference
            .child(FOLDER_NAME_APARTMENTS_IMAGES)
            .child(UUID.randomUUID().toString())
            .putStream(stream)
    }

}