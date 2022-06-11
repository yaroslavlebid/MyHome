package yaroslavlebid.apps.myhome.ui.home.apartments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.databinding.ItemApartmentBinding
import yaroslavlebid.apps.myhome.utils.setImageFromUrl

class ApartmentListAdapter(private val apartments: MutableList<Apartment>) :
    RecyclerView.Adapter<ApartmentListAdapter.ApartmentsViewHolder>() {

    var onItemClickListener: ((Apartment) -> Unit)? = null
    var onMapClickListener: ((Apartment) -> Unit)? = null
    var bookmarkClickListener: ((Apartment) -> Unit)? = null
    var isSavedInFavorite: Boolean = false

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
                binding.location.text =
                    "${location.city}, ${location.street}, ${location.numberOfHouse}"
                binding.chipRating.text = "$ratingAvg / 5"
                binding.chipPrice.text =
                    "From ${minRoomPrice.amountOfMoney} ${minRoomPrice.currency.sign}"
                binding.addToSaved.isChecked = isSavedInFavorite

                binding.root.setOnClickListener { onItemClickListener?.invoke(this) }
                binding.addToSaved.setOnCheckedChangeListener { _, isChecked ->
                    bookmarkClickListener?.invoke(this)
                    binding.addToSaved.isChecked = isChecked
                }
                binding.mapButton.setOnClickListener { onMapClickListener?.invoke(this) }
                binding.detailsButton.setOnClickListener { onItemClickListener?.invoke(this) }
            }
        }
    }

    override fun getItemCount() = apartments.size

    inner class ApartmentsViewHolder(val binding: ItemApartmentBinding) :
        RecyclerView.ViewHolder(binding.root)
}