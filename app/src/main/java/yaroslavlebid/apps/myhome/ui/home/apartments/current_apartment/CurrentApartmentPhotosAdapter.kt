package yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import yaroslavlebid.apps.myhome.databinding.ItemApartmentBinding
import yaroslavlebid.apps.myhome.databinding.ItemOpenedApartmentBinding
import yaroslavlebid.apps.myhome.ui.home.my_profile.add_apartment.ApartmentPhotosAdapter
import yaroslavlebid.apps.myhome.utils.setImageFromUrl

class CurrentApartmentPhotosAdapter(private val photosUrl: List<String>) :
    RecyclerView.Adapter<CurrentApartmentPhotosAdapter.CurrentApartmentPhotosViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrentApartmentPhotosAdapter.CurrentApartmentPhotosViewHolder {
        val binding =
            ItemOpenedApartmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrentApartmentPhotosViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CurrentApartmentPhotosAdapter.CurrentApartmentPhotosViewHolder,
        position: Int
    ) {
        with(holder) {
            photosUrl[position].apply {
                binding.photo.setImageFromUrl(this)
            }
        }
    }

    override fun getItemCount(): Int = photosUrl.size

    inner class CurrentApartmentPhotosViewHolder(val binding: ItemOpenedApartmentBinding) :
        RecyclerView.ViewHolder(binding.root)

}