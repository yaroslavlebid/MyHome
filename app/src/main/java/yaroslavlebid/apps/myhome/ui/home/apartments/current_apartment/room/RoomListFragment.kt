package yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment.room

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.databinding.FragmentRoomListBinding

class RoomListFragment : Fragment(R.layout.fragment_room_list) {

    private val roomListViewModel: RoomListViewModel by viewModel()

    private val args: RoomListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRoomListBinding.bind(view)
        val apartment = args.apartment
        binding.run {
            toolbar.title = apartment.title
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        roomListViewModel.rooms.observe(viewLifecycleOwner) {
            val adapter = RoomListAdapter(it).apply {
                onBookClickListener = {
                    val action = RoomListFragmentDirections.actionRoomListFragmentToOrderFragment(
                        room = it,
                        isRoom = true,
                        apartment = apartment,
                        selectedPersons = args.people,
                        selectedDate = args.date
                    )
                    findNavController().navigate(action)
                }
            }
            binding.roomsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.roomsRecyclerView.adapter = adapter
        }

        roomListViewModel.loadRooms(apartment.id)
    }
}