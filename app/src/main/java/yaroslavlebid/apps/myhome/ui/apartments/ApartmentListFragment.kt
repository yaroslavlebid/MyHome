package yaroslavlebid.apps.myhome.ui.apartments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentApartmentListBinding

class ApartmentListFragment : Fragment(R.layout.fragment_apartment_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentApartmentListBinding.bind(view)

        initListeners(binding)
    }

    private fun initListeners(binding: FragmentApartmentListBinding) {
        binding.floatingActionButton.setOnClickListener { TODO("navigate to form add apartment") }
    }

}