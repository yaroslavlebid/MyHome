package yaroslavlebid.apps.myhome.utils

import java.text.SimpleDateFormat
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun dateToString(day: Int, month: Int, year: Int) = "$day/$month/$year"

fun stringToDate(stringDate: String): LocalDate {
    val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    try {
        val date = LocalDate.parse(stringDate, format)
        return date
    } catch (e: Throwable) {
        Timber.e("Fail when trying to parse date of birth", e)
    }
    return LocalDate.of(1970, 1, 1)
}

fun Long.toSelectedDate() : String {
    val simpleDateFormat = SimpleDateFormat("EEE dd.MM", Locale.getDefault())
    return simpleDateFormat.format(this)
}
