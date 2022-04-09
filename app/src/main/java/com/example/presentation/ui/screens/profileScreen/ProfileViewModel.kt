package com.example.presentation.ui.screens.profileScreen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.utility.Country
import com.example.utility.TAG
import com.example.utility.getCountriesList
import java.text.SimpleDateFormat
import java.util.*

class ProfileViewModel : ViewModel() {

    init {
        Log.d(TAG,"Profile View Model has been started")
    }

    val countriesList = getCountriesList()
    var mobileCountry by mutableStateOf(Country("ad", "376", "Andorra"))
    var mobileCountryCode by mutableStateOf("")
    var mobileNumber by mutableStateOf("")



    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var password by mutableStateOf("")


    var dateOfBirth by mutableStateOf("")


    var pickedImage by  mutableStateOf<Bitmap?>(null)






    private var dateFormat = "yyyy-MM-dd"
    fun showDatePickerDialog(context: Context) {
        val calendar = getCalendar()
        DatePickerDialog(
            context, { _, year, month, day ->
                dateOfBirth = getPickedDateAsString(year, month, day)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }
    private fun getCalendar(): Calendar {
        return if (dateOfBirth.isEmpty())
            Calendar.getInstance()
        else
            getLastPickedDateCalendar()
    }


    @SuppressLint("SimpleDateFormat")
    private fun getLastPickedDateCalendar(): Calendar {
        val dateFormat = SimpleDateFormat(dateFormat)
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(dateOfBirth) as Date
        return calendar
    }

    @SuppressLint("SimpleDateFormat")
    private fun getPickedDateAsString(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat(dateFormat)
        return dateFormat.format(calendar.time)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG,"Profile View Model has been destroyed")
    }
}