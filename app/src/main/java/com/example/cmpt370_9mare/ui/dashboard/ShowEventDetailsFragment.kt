package com.example.cmpt370_9mare.ui.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.databinding.FragmentEventDetailsBinding

class ShowEventDetailsFragment(
    private val event: ScheduleEvent,
    private val action: NavDirections
) : DialogFragment() {

    companion object {
        const val EVENT_DETAILS = "eventDetails_tag"
    }

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            _binding = FragmentEventDetailsBinding.inflate(layoutInflater)

            // Inflate and set the layout for the dialog
            builder.setView(binding.root)
                .setMessage("Event Details")
                // Add action buttons
                .setPositiveButton(R.string.edit) { dialog, _ ->
                    dialog.cancel()
                    NavHostFragment.findNavController(this).navigate(action)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            titleValue.text = event.title
            locationValue.text = event.location
            dateValue.text = event.date
            timeValue.text =
                if (event.time_to.isNotEmpty()) "${event.time_from} to ${event.time_to}" else event.time_from
            urlValue.text = event.url
            notesValue.text = event.notes
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}