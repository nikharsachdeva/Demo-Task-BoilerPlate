package com.demo.app.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demo.app.MainActivity
import com.demo.app.R
import io.reactivex.Observable
import java.math.RoundingMode
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.concurrent.TimeUnit

//private val ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
val ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
fun Context.convertDpToPixels(dp: Float): Int {
    val scale: Float = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun EditText.showKeyboard(
) {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
            InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideKeyboard(
) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
            InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Context.isPackageInstalled(packageName: String): Boolean {
    return try {
        packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}
fun Double.roundOffDecimal(): Double? {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}

fun Context.getFirstAndLastDate(): Pair<String?, String?> {
    val sdf: DateFormat = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
    var beginning: Date? = null
    var end: Date? = null
    var pair = Pair("", "")
    run {
        val calendar = getCalendarForNow()
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        setTimeToBeginningOfDay(calendar)
        beginning = calendar.time
    }
    run {
        val calendar = getCalendarForNow()
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        setTimeToEndOfDay(calendar)
        end = calendar.time
    }
    return pair.copy(first = sdf.format(beginning!!), second = sdf.format(end!!))
}

private fun getCalendarForNow(): Calendar {
    val calendar = GregorianCalendar.getInstance()
    calendar.time = Date()
    return calendar
}

private fun setTimeToBeginningOfDay(calendar: Calendar) {
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
}

private fun setTimeToEndOfDay(calendar: Calendar) {
    calendar[Calendar.HOUR_OF_DAY] = 23
    calendar[Calendar.MINUTE] = 59
    calendar[Calendar.SECOND] = 59
    calendar[Calendar.MILLISECOND] = 999
}

fun Long.getStandardizeCount(): String {
    return when {
        this < 1000 -> {
            this.toString()
        }

        this in 1000..999999 -> {
            (this / 1000).toString() + "K"
        }

        else -> {
            (this / 1000000).toString() + "M"
        }
    }
}

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
    (this as MainActivity).showSnackBar("Copied to clipboard")
}

fun Button.makeButtonDisabled() {
    alpha = 0.7f
    isEnabled = false
}

fun Button.makeButtonEnabled() {
    alpha = 1.0f
    isEnabled = true
}

fun View.makeViewVisible() {
    visibility = View.VISIBLE
}

fun View.makeViewGone() {
    visibility = View.GONE
}


fun View.makeViewInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeViewInvisible() {
    visibility = View.INVISIBLE
}

fun Int.singleDateToDoubleDate(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

fun ImageView.loadImageWithGlide(url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_foreground))
        .apply(RequestOptions.errorOf(R.drawable.ic_launcher_foreground))
        .into(this)
}

fun ImageView.loadImageWithGlide(url: Uri) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_foreground))
        .apply(RequestOptions.errorOf(R.drawable.ic_launcher_foreground))
        .into(this)
}

fun String.getFormattedDate(): String? {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val output = SimpleDateFormat("dd-MM-yyyy hh:mm a")
    val d = sdf.parse(this)
    val formattedTime = output.format(d)
    return formattedTime
}

fun String.getFormattedDateWithTime(): String? {
    //ISO to 24 Jan 2022
    val sdf = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
    val output = SimpleDateFormat("dd LLL yyyy hh:mm aa")
    val d = sdf.parse(this)
    val formattedTime = output.format(d)
    return formattedTime
}

fun String.isoToOnlyMonth(): String? {
    //ISO to 24 Jan 2022
    val sdf = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
    val output = SimpleDateFormat("LLL")
    val d = sdf.parse(this)
    val formattedTime = output.format(d)
    return formattedTime
}

fun String.isoToOnlyDate(): String? {
    //ISO to 24 Jan 2022
    val sdf = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
    val output = SimpleDateFormat("d")
    val d = sdf.parse(this)
    val formattedTime = output.format(d)
    return formattedTime
}

fun String.isoToOnlyYear(): String? {
    //ISO to 24 Jan 2022
    val sdf = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
    val output = SimpleDateFormat("yyyy")
    val d = sdf.parse(this)
    val formattedTime = output.format(d)
    return formattedTime
}

fun String.getFormattedDateInString(): String? {
    //ISO to 24 Jan 2022
    val sdf = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
    val output = SimpleDateFormat("dd LLL yyyy")
    val d = sdf.parse(this)
    val formattedTime = output.format(d)
    return formattedTime
}

fun String.getFormattedDateInString2(): String? {
    //12/1/2022 to 24 Jan 2022
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    val output = SimpleDateFormat("dd LLL yyyy")
    val d = sdf.parse(this)
    val formattedTime = output.format(d)
    return formattedTime
}

fun String.normalDateToISO(): String? {
    //12/1/2022 to ISO
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    val output = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT)
    val d = sdf.parse(this)
    val formattedTime = output.format(d)
    return formattedTime
}

fun Int.getOrderStatus(): String {

    when (this) {
        0 -> {
            return "Order Placed"
        }

        1 -> {
            return "Order Accepted"
        }

        2 -> {
            return "Rider Assigned For Pickup"
        }

        3 -> {
            return "Out For Pickup"
        }

        4 -> {
            return "Bill Generated"
        }

        5 -> {
            return "Order Received By Vendor"
        }

        6 -> {
            return "Service In Progress"
        }

        7 -> {
            return "Order Packed"
        }

        8 -> {
            return "Rider Assigned For Delivery"
        }

        9 -> {
            return "Out for delivery"
        }

        10 -> {
            return "Delivered"
        }
    }
    return ""
}

fun Observable<CharSequence>.mapToString(): Observable<String> = this.map { it.toString() }

fun <T> Observable<T>.throttleFirstShort() = this.throttleFirst(500L, TimeUnit.MILLISECONDS)!!

fun String.getProgress(): Pair<Int, Double>? {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var pair = Pair(0, 0.0)
    try {
        val deadlineDate = format.parse(this)
        val currentDate = format.parse(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(Date())
        )
        return if (deadlineDate != null && currentDate != null) {
            val diff: Long = deadlineDate.time - currentDate.time
            val leftSeconds: Int = (diff / 1000).toInt()
            val totalSeconds: Int = 86400
            val progress: Double = ((leftSeconds.toDouble() / totalSeconds) * 100)
            pair = pair.copy(first = leftSeconds, second = progress)
            pair
        } else {
            pair
        }
    } catch (e: ParseException) {
        e.printStackTrace()
        return null
    }
}



fun String.getRatingAnimation(): String {

    when (this) {

        "1" -> {
            return "star_1.json"
        }

        "2" -> {
            return "star_2.json"

        }

        "3" -> {
            return "star_3.json"

        }

        "4" -> {
            return "star_4.json"

        }

        "5" -> {
            return "star_5.json"

        }
    }

    return "star.json"

}


