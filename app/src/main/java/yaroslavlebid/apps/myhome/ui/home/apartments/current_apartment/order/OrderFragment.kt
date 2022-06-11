package yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment.order

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.data.apartment.TypeOfBed
import yaroslavlebid.apps.myhome.databinding.FragmentOrderBinding
import yaroslavlebid.apps.myhome.utils.setImageFromUrl

class OrderFragment : Fragment(R.layout.fragment_order) {

    private val orderFragmentArgs: OrderFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentOrderBinding.bind(view)

        initViews(binding)
    }

    private fun initViews(binding: FragmentOrderBinding) {
        binding.run {
            itemRoom.bookButton.visibility = View.GONE
            itemRoom.price.visibility = View.GONE
            itemRoom.priceIcon.visibility = View.GONE
            if (orderFragmentArgs.isRoom) {
                val room = orderFragmentArgs.room ?: return
                itemRoom.photo.setImageFromUrl(room.photo.mediumImageUrl)
                itemRoom.roomName.text = room.name
                itemRoom.personCountChip.text = room.peopleCapacity.toString()
                var single = 0
                var double = 0
                var bunks = 0
                room.beds.forEach {
                    if (it.type == TypeOfBed.SINGLE) single++
                    if (it.type == TypeOfBed.DOUBLE) double++
                    if (it.type == TypeOfBed.BUNK) bunks++

                }
                if (single == 0) itemRoom.singleBedChip.visibility = View.GONE else itemRoom.singleBedChip.text = single.toString()
                if (double == 0) itemRoom.doubleBedChip.visibility = View.GONE else itemRoom.doubleBedChip.text = double.toString()
                if (bunks == 0) itemRoom.bunkBedChip.visibility = View.GONE else itemRoom.bunkBedChip.text = bunks.toString()
                val days = 1
                val price = room.price.amountOfMoney*days
                totalPriceText.text = "${price} ${room.price.currency}"
            } else {

                val apartment = orderFragmentArgs.apartment
                itemRoom.photo.setImageFromUrl(apartment.photos[0].mediumImageUrl)
                itemRoom.roomName.text = apartment.title
                itemRoom.personCountChip.text = apartment.peopleCapacity.toString()
                var single = 0
                var double = 0
                var bunks = 0
                apartment.beds.forEach {
                    if (it.type == TypeOfBed.SINGLE) single++
                    if (it.type == TypeOfBed.DOUBLE) double++
                    if (it.type == TypeOfBed.BUNK) bunks++

                }
                if (single == 0) itemRoom.singleBedChip.visibility = View.GONE else itemRoom.singleBedChip.text = single.toString()
                if (double == 0) itemRoom.doubleBedChip.visibility = View.GONE else itemRoom.doubleBedChip.text = double.toString()
                if (bunks == 0) itemRoom.bunkBedChip.visibility = View.GONE else itemRoom.bunkBedChip.text = bunks.toString()
                val days = 1
                val price = apartment.minRoomPrice.amountOfMoney*days
                totalPriceText.text = "${price} ${apartment.minRoomPrice.currency}"
            }

            binding.selectedDateChip.text = orderFragmentArgs.selectedDate
            binding.selectedPersonsChip.text = orderFragmentArgs.selectedPersons


            goToPayment.setOnClickListener {
                val action = OrderFragmentDirections.actionOrderFragmentToPaymentFragment()
                findNavController().navigate(action)
            }

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}