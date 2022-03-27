package com.example.cmpt370_9mare.ui.event

import android.app.AlertDialog
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import com.example.cmpt370_9mare.R
import com.example.cmpt370_9mare.data.schedule_event.ScheduleEvent
import com.example.cmpt370_9mare.databinding.FragmentCreateEventBinding
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class CreateEventValidation(
    private val fragment: CreateEventFragment,
    private val binding: FragmentCreateEventBinding
) {
    private val isValidated get() = validateTitle() && validateTimeInput()

    private fun validateTitle(): Boolean {
        return when {
            binding.inputTitle.text.toString().trim().isEmpty() -> {
                binding.eventTitle.error = "Required Title"
                binding.eventTitle.requestFocus()
                false
            }
            binding.inputTitle.text.toString().length > 30 -> {
                binding.eventTitle.error = "Title cannot exceeding 30 letters"
                false
            }
            else -> {
                binding.eventTitle.isErrorEnabled = false
                true
            }
        }
    }

    private fun validateTimeInput(): Boolean {
        return if (binding.inputTimeFrom.text.toString() != fragment.getString(R.string.all_day) && binding.inputTimeFrom.text.toString() != "" && binding.inputTimeTo.text.toString() != "") {
            if (LocalTime.parse(binding.inputTimeFrom.text.toString()) >= LocalTime.parse(binding.inputTimeTo.text.toString())) {
                binding.dateTimeLayout.error = "TimeTo must be later than TimeFrom"
                false
            } else {
                binding.dateTimeLayout.isErrorEnabled = false
                true
            }
        } else true
    }


    private fun showSubmitButton(boolean: Boolean) {
        binding.submitCreateEvent.visibility = if (boolean) View.VISIBLE else View.INVISIBLE
    }

    /**
     * Used to build and create a pop-up menu detailing all conflict events
     * present from a database query
     */
    fun showConflictDialog(conflictEvents: List<ScheduleEvent>) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(fragment.context)
        builder.setTitle("Conflict Found")

        // Get all conflict events and format to string
        var txt = ""
        conflictEvents.forEach {
            txt += "${it.title}: ${it.time_from} - ${it.time_to}\n"
        }
        builder.setMessage(txt)

        builder.setNegativeButton("OK") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    fun setUpValidations() {
        binding.apply {
            inputTitle.addTextChangedListener(TextFieldValidation(inputTitle))
            inputTimeFrom.addTextChangedListener(TextFieldValidation(inputTimeFrom))
            inputTimeTo.addTextChangedListener(TextFieldValidation(inputTimeTo))
        }
    }

    /*
* applying text watcher on each text field
*/
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.input_title -> validateTitle()
                R.id.inputTimeFrom -> validateTimeInput()
                R.id.inputTimeTo -> validateTimeInput()
            }

            showSubmitButton(isValidated)
        }
    }
}