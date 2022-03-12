package com.example.cmpt370_9mare.ui.event

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.databinding.FragmentNewEventBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_REPEAT = "repeat"
private const val TAG = "newEventFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [NewEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewEventFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var repeat: String? = null


    private var _binding: FragmentNewEventBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repeat = it.getString(ARG_REPEAT)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            newEventFragment = this@NewEventFragment
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment NewEventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(repeat: String) =
            NewEventFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_REPEAT, repeat)
                }
            }
    }

    fun onSelectRepeatNever() {
        val action = NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment(
        )
        findNavController().navigate(action)

    }

    fun onSelectRepeatEveryDay() {
        /*CreateEventFragment.newInstance("every-day")*/
        val action = NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment(
        )
        findNavController().navigate(action)

    }

    fun onSelectRepeatEveryWeek() {
        val action = NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment(
        )
        findNavController().navigate(action)

    }

    fun onSelectRepeatEvery2Weeks() {
        val action = NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment(
        )
        findNavController().navigate(action)

    }

    fun onSelectRepeatEveryMonth() {
        val action = NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment(
        )
        findNavController().navigate(action)

    }

    fun onSelectRepeatEveryYear() {
        val action = NewEventFragmentDirections.actionNewEventFragmentToCreateEventFragment(
        )
        findNavController().navigate(action)

    }

}