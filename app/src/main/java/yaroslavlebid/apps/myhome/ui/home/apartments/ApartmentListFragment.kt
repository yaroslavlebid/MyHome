package yaroslavlebid.apps.myhome.ui.home.apartments

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lapism.search.widget.MaterialSearchView
import com.lapism.search.widget.NavigationIconCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.data.apartment.Apartment
import yaroslavlebid.apps.myhome.databinding.FragmentApartmentListBinding
import yaroslavlebid.apps.myhome.ui.home.apartments.adapter.ApartmentListAdapter

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

        binding.materialSearchBar.apply {
            navigationIconCompat = NavigationIconCompat.SEARCH
            setHint("Search")
            setOnClickListener {
                binding.materialSearchView.requestFocus()
                binding.searchHelper.root.visibility = View.VISIBLE
                recyclerViewConstraintsTopToBottomOf(binding.recyclerView, binding.searchHelper.root.id)
                binding.filterAndSortLayout.visibility = View.GONE
            }
            setNavigationOnClickListener {
                binding.materialSearchView.requestFocus()
                binding.searchHelper.root.visibility = View.VISIBLE
                recyclerViewConstraintsTopToBottomOf(binding.recyclerView, binding.searchHelper.root.id)
                binding.filterAndSortLayout.visibility = View.GONE
            }
        }


        binding.materialSearchView.apply {
            navigationIconCompat = NavigationIconCompat.ARROW
            setNavigationOnClickListener {
                binding.materialSearchView.clearFocus()
                binding.searchHelper.root.visibility = View.GONE
                binding.filterAndSortLayout.visibility = View.VISIBLE
                recyclerViewConstraintsTopToBottomOf(binding.recyclerView, binding.filterAndSortLayout.id)
            }
            setHint("Where are you going?")
            setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: CharSequence) {
                }

                override fun onQueryTextSubmit(query: CharSequence) {

                }
            })
            setOnFocusChangeListener(object : MaterialSearchView.OnFocusChangeListener {
                override fun onFocusChange(hasFocus: Boolean) {

                }
            })
        }

        //searchHelper.horizontalScroll.setOnClickListener { searchHelper.horizontalScroll.requestFocus() }



        //apartmentViewModel.addMockApartmentsToDb()
    }

    private fun initViews(binding: FragmentApartmentListBinding) {
        val adapter = ApartmentListAdapter(apartmentList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun initListeners(binding: FragmentApartmentListBinding) {
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

    private fun recyclerViewConstraintsTopToBottomOf(recyclerView: RecyclerView, viewId: Int) {
        val params = recyclerView.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom = viewId
        recyclerView.requestLayout()
    }

}