package yaroslavlebid.apps.myhome.ui.apartments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.databinding.ItemApartmentBinding
import yaroslavlebid.apps.myhome.utils.setImageFromUrl
import java.text.DecimalFormat

class ApartmentListAdapter(private val apartments: MutableList<Apartment>) :
    RecyclerView.Adapter<ApartmentListAdapter.ApartmentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentsViewHolder {
        val binding =
            ItemApartmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApartmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApartmentsViewHolder, position: Int) {
        with(holder) {
            apartments[position].apply {
                binding.photo.setImageFromUrl(photos[coverPhotoId].mediumImageUrl)
                binding.title.text = title
                binding.description.text = description
                binding.location.text = "${location.city}, ${location.street}, ${location.numberOfHouse}"
                binding.chipRating.text = DecimalFormat("#0.0").format(ratingAvg)
            }
        }
    }

    override fun getItemCount() = apartments.size

    inner class ApartmentsViewHolder(val binding: ItemApartmentBinding) :
        RecyclerView.ViewHolder(binding.root)
}