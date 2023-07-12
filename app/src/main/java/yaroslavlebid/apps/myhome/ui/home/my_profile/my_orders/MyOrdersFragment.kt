package yaroslavlebid.apps.myhome.ui.home.my_profile.my_orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentMyOrdersBinding
import yaroslavlebid.apps.myhome.utils.setImageFromUrl

class MyOrdersFragment : Fragment(R.layout.fragment_my_orders){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyOrdersBinding.bind(view)
        binding.room.roomName.text = "Квартира у центрі Львова"
        binding.room.photo.setImageFromUrl("https://a0.muscache.com/im/pictures/44781710/30652268_original.jpg?im_w=1200")
    }
}