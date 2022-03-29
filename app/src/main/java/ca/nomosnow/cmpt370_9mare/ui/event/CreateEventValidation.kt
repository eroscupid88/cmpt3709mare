package ca.nomosnow.cmpt370_9mare.ui.event

import android.app.AlertDialog
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import ca.nomosnow.cmpt370_9mare.R
import ca.nomosnow.cmpt370_9mare.data.schedule_event.ScheduleEvent
import ca.nomosnow.cmpt370_9mare.databinding.FragmentCreateEventBinding
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
        binding.apply {
            return when {
                allDay.isChecked -> true
                inputTimeFrom.text.toString() >= inputTimeTo.text.toString() -> {
                    dateTimeLayout.error = "TimeTo must be later than TimeFrom"
                    false
                }
                else -> {
                    dateTimeLayout.isErrorEnabled = false
                    true
                }
            }
        }
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