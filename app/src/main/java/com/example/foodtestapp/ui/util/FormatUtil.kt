package FormatUtil

import java.time.LocalDateTime

fun getCurrentDateFormat(): String {
    val current = LocalDateTime.now()
    return "${current.dayOfMonth} ${getMonthName(current.monthValue)}, ${current.year}"
}

private fun getMonthName(monthValue: Int): String {
    return when (monthValue) {
        1 -> "Января"
        2 -> "Февраля"
        3 -> "Марта"
        4 -> "Апреля"
        5 -> "Мая"
        6 -> "Июня"
        7 -> "Июля"
        8 -> "Августа"
        9 -> "Сентября"
        10 -> "Октября"
        11 -> "Ноября"
        12 -> "Декабря"
        else -> ""
    }
}