package yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPaymentBinding.bind(view)
        binding.run {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            confirmButton.setOnClickListener {
                val action = PaymentFragmentDirections.actionPaymentFragmentToPaymentResultFragment()
                findNavController().navigate(action)
            }
        }
    }
}