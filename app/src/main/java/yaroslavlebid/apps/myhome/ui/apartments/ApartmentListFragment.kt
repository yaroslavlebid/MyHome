package yaroslavlebid.apps.myhome.ui.apartments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.databinding.FragmentApartmentListBinding
import yaroslavlebid.apps.myhome.ui.apartments.adapter.ApartmentListAdapter

class ApartmentListFragment : Fragment(R.layout.fragment_apartment_list) {

    private val apartmentViewModel: ApartmentListViewModel by viewModel()
    val apartmentList: MutableList<Apartment> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentApartmentListBinding.bind(view)

        initViews(binding)
        initListeners(binding)
        initObservers(binding)

        apartmentViewModel.requestApartments()
        //apartmentViewModel.addMockApartmentsToDb()
    }

    private fun initViews(binding: FragmentApartmentListBinding) {
        val adapter = ApartmentListAdapter(apartmentList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun initListeners(binding: FragmentApartmentListBinding) {
        binding.floatingActionButton.setOnClickListener {
            TODO("navigate to form add apartment")
        }
    }

    private fun initObservers(binding: FragmentApartmentListBinding) {
        apartmentViewModel.apartments.observe(viewLifecycleOwner) { resultList ->
            apartmentList.clear()
            apartmentList.addAll(resultList)
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

        apartmentViewModel.isLoading.observe(viewLifecycleOwner) {
            // todo: visible loading indicator
        }
    }

}