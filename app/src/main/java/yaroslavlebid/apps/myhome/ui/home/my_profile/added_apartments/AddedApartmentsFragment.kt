package yaroslavlebid.apps.myhome.ui.home.my_profile.added_apartments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentAddedApartmentsBinding

class AddedApartmentsFragment : Fragment(R.layout.fragment_added_apartments) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddedApartmentsBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener {
            val action = AddedApartmentsFragmentDirections.actionAddedApartmentsFragmentToProfile()
            findNavController().navigate(action)
        }
    }
}