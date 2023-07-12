package yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentPaymentResultBinding

class PaymentResultFragment : Fragment(R.layout.fragment_payment_result) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPaymentResultBinding.bind(view)
        binding.backToHome.setOnClickListener {
            val action = PaymentResultFragmentDirections.actionPaymentResultFragmentToSearch()
            findNavController().navigate(action)
        }
    }
}