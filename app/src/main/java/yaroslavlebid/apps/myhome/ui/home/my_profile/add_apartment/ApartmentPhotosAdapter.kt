package yaroslavlebid.apps.myhome.ui.home.my_profile.add_apartment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.ItemPhotoAddApartmentBinding
import yaroslavlebid.apps.myhome.utils.setImageFromUrl

class ApartmentPhotosAdapter(private val photosUrl: MutableList<String>) :
    RecyclerView.Adapter<ApartmentPhotosAdapter.ApartmentsViewHolder>() {

    var onLongPhotoClick: ((Int) -> Unit)? = null
    var isEmpty: Boolean = true
    var isLoading: Boolean = false
    var pickImages: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentsViewHolder {
        val binding =
            ItemPhotoAddApartmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApartmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApartmentsViewHolder, position: Int) {
        with(holder) {
            photosUrl[position].apply {
                if (isLoading) {
                    binding.photo.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.photo.visibility = View.VISIBLE
                }

                if (!isEmpty)
                    binding.photo.setImageFromUrl(this, false)
                else {
                    binding.photo.setImageResource(R.drawable.add_photo)
                    binding.photo.setOnClickListener {
                        pickImages?.invoke()
                    }
                }
                binding.photo.setOnLongClickListener {
                    onLongPhotoClick?.invoke(position)
                    binding.isCover.visibility = View.VISIBLE
                    true
                }
            }
        }
    }

    override fun getItemCount() = photosUrl.size

    inner class ApartmentsViewHolder(val binding: ItemPhotoAddApartmentBinding) :
        RecyclerView.ViewHolder(binding.root)
}