package yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.data.apartment.Room
import yaroslavlebid.apps.myhome.data.apartment.TypeOfBed
import yaroslavlebid.apps.myhome.databinding.ItemApartmentBinding
import yaroslavlebid.apps.myhome.databinding.ItemRoomBinding
import yaroslavlebid.apps.myhome.utils.setImageFromUrl

class RoomListAdapter(private val rooms: List<Room>) :
    RecyclerView.Adapter<RoomListAdapter.RoomsViewHolder>() {

    var onBookClickListener: ((Room) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val binding =
            ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        with(holder) {
            rooms[position].apply {
                binding.photo.setImageFromUrl(photo.mediumImageUrl)
                binding.roomName.text = name
                binding.personCountChip.text = peopleCapacity.toString()
                var single = 0
                var double = 0
                var bunks = 0
                beds.forEach {
                    if (it.type == TypeOfBed.SINGLE) single++
                    if (it.type == TypeOfBed.DOUBLE) double++
                    if (it.type == TypeOfBed.BUNK) bunks++

                }
                if (single == 0) binding.singleBedChip.visibility = View.GONE else binding.singleBedChip.text = single.toString()
                if (double == 0) binding.doubleBedChip.visibility = View.GONE else binding.doubleBedChip.text = double.toString()
                if (bunks == 0) binding.bunkBedChip.visibility = View.GONE else binding.bunkBedChip.text = bunks.toString()
                binding.price.text = "${price.amountOfMoney} ${price.currency}/в день"
                binding.bookButton.setOnClickListener {
                    onBookClickListener?.invoke(this)
                }
            }
        }
    }

    override fun getItemCount() = rooms.size

    inner class RoomsViewHolder(val binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root)
}