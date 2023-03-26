package com.jae1jeong.currencycalculate.presentation.binding_adapter

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.jae1jeong.currencycalculate.R
import com.jae1jeong.currencycalculate.utils.Validator
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object BindingAdapter {

    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    @androidx.databinding.BindingAdapter("android:timeStamp")
    fun setTimeStamp(view: TextView, timeStamp: Long) {
        if(timeStamp == 0L) return
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy M-d HH:mm")
        val dateTime = Instant.ofEpochSecond(timeStamp).atZone(ZoneId.systemDefault()).format(dateTimeFormatter)
        view.text = dateTime

    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("android:currentRate","android:fromTo")
    fun setRate(view: TextView, currentRate: Double, toFrom: String) {
        val context = view.context
        val to = toFrom.substring(0,3)
        val from = toFrom.substring(4,6)
        val rateCommaFormatted = String.format("%,.2f", currentRate)
        view.text = String.format(context.getString(R.string.rate_text_format),rateCommaFormatted,from,to)
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("android:currentRate","android:sendAmount","android:receiveCountryCode")
    fun setResultSendAmount(view: TextView, currentRate: Double, sendAmount: Double,receiveCountryCode:String) {
        val context = view.context
        val isValid = Validator.checkValidInRange(sendAmount)
        view.visibility = if(isValid) View.VISIBLE else View.INVISIBLE
        val result = currentRate * sendAmount
        val resultCommaFormatted = String.format("%,.2f", result)
        val resultTextWithFormatted = String.format(context.getString(R.string.send_amount_result_format),resultCommaFormatted,receiveCountryCode)
        view.text = resultTextWithFormatted
    }
}