package com.neowise.spic.util

import java.text.SimpleDateFormat
import java.util.*

data class DateRange(val start: SimpleDate, val end: SimpleDate)

data class TimeRange(val start: SimpleTime, val end: SimpleTime) {
    override fun toString(): String {
        return "$start - $end"
    }
}

private fun Int.format(): String {
    return (if (this < 10) "0$this" else this.toString())
}

data class SimpleDate(
    val year: Int,
    val month: Int,
    val day: Int
) {

    private var date: Date? = null

    fun addDay(days: Int): SimpleDate {
        val c = Calendar.getInstance()
        c.time = date
        c.add(Calendar.DATE, days)
        val newDate = c.time
        return parse(newDate)
    }

    companion object {

        fun now(): SimpleDate {
            return parse(Date())
        }

        private fun parse(date: Date) : SimpleDate {
            return SimpleDate(date.year + 1900, date.month+1, date.date).apply {
                this.date = date
            }
        }

        fun parse(string: String): SimpleDate {
            val date = SimpleDateFormat("dd.MM.yyyy").parse(string)
            return parse(date)
        }
    }

    fun format(): String {
        return "${year.format()}-${month.format()}-${day.format()}"
    }

    override fun toString(): String {
        return "${day.format()}.${month.format()}.${year.format()}"
    }
}

data class SimpleTime(
    val hours: Int,
    val minutes: Int,
    val seconds: Int
) {
    constructor(hours: Int, minutes: Int) : this(hours, minutes, 0)

    companion object {
        fun parse(source: String): SimpleTime {
            val (hours, minutes) = source.split(":")
            return SimpleTime(hours.toInt(), minutes.toInt())
        }
    }

    override fun toString(): String {
        return "${hours.format()}:${minutes.format()}"
    }
}

enum class Month {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER
    ;

    val text: String = name

    override fun toString(): String {
        return text
    }

    companion object {
        fun getMonth(month: Int): Month {
            return values()[month]
        }
    }
}