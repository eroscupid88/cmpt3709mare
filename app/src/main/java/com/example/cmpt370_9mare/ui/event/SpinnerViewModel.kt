package com.example.cmpt370_9mare.ui.event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private val TAG = "Spinner View Model"

class SpinnerViewModel : ViewModel() {

    private val _lengthSelection = MutableLiveData<Int>()

    val lengthSelection: LiveData<Int>
        get() = _lengthSelection

    private val _dropdown_selected = MutableLiveData<Array<Int>>()
    val dropdown_selected: LiveData<Array<Int>> get() = _dropdown_selected

    private val _typeSelection = MutableLiveData<Int>()
    val typeSelection: LiveData<Int> get() = _typeSelection

    private val day_array: Array<Int> = arrayOf(
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        23,
        24,
        25,
        26,
        27,
        28,
        29,
        30
    )
    private val week_array: Array<Int> = arrayOf(1, 2, 3, 4)
    private val month_array: Array<Int> = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)

    init {
        _dropdown_selected.value = day_array
    }

    /**
     * setSpinnerSelected function set value of lengthSelection
     */
    fun setSpinnerSelected(position: Int) {
        _lengthSelection.value = _dropdown_selected.value?.get(position)
        Log.d(
            TAG, "position: ${_lengthSelection.value}"
        )
    }


    /**
     * setRepeatEvery function set Livedata to the choosen array for spinner dropdown
     */
    fun setRepeatEvery(position: Int) {
        _dropdown_selected.value = when (position) {
            0 -> day_array
            1 -> week_array
            else -> {
                month_array
            }
        }

    }

    /**
     * type input could be 1, 2 or 3
     * 1: day(s)
     * 2: week(s)
     * 3: month(s)
     */
    fun setTypeSelected(type: Int) {
        _typeSelection.value = type
    }


}