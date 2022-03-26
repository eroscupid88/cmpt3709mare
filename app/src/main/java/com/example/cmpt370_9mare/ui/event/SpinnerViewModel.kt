package com.example.cmpt370_9mare.ui.event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "Spinner View Model"

class SpinnerViewModel : ViewModel() {

    private val _lengthSelection = MutableLiveData<Int>()
    val lengthSelection: LiveData<Int> get() = _lengthSelection

    private val _dropdownSelected = MutableLiveData<Array<Int>>()
    val dropdownSelected: LiveData<Array<Int>> get() = _dropdownSelected

    private val _typeSelection = MutableLiveData<Int>()
    val typeSelection: LiveData<Int> get() = _typeSelection

    private val _eventTypeSelection = MutableLiveData<Int>()
    val eventTypeSelection: LiveData<Int> get() = _eventTypeSelection


    private val dayArray: Array<Int> = (1..30).toList().toTypedArray()

    //        1,
//        2,
//        3,
//        4,
//        5,
//        6,
//        7,
//        8,
//        9,
//        10,
//        11,
//        12,
//        13,
//        14,
//        15,
//        16,
//        17,
//        18,
//        19,
//        20,
//        21,
//        22,
//        23,
//        24,
//        25,
//        26,
//        27,
//        28,
//        29,
//        30
//    )
    private val weekArray: Array<Int> = (1..4).toList().toTypedArray()
    private val monthArray: Array<Int> = (1..11).toList().toTypedArray()

    init {
        _dropdownSelected.value = dayArray
    }

    /**
     * setSpinnerSelected function set value of lengthSelection
     */
    fun setSpinnerSelected(position: Int) {
        _lengthSelection.value = _dropdownSelected.value?.get(position)
        Log.d(TAG, "position: ${_lengthSelection.value}")
    }


    /**
     * setRepeatEvery function set Livedata to the choosen array for spinner dropdown
     */
    fun setRepeatEvery(position: Int) {
        _dropdownSelected.value = when (position) {
            0 -> dayArray
            1 -> weekArray
            else -> monthArray
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


    fun setEventTypeSelection(type: Int) {
        _eventTypeSelection.value = type
    }


}