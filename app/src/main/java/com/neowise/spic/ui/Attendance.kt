package com.neowise.spic.ui

import android.widget.TextView
import com.neowise.spic.R
import com.neowise.spic.model.AttendanceState

enum class Attendance(val model: AttendanceState, val icon: Int, val color: Int, val text: Int) {

    ATTENDED(AttendanceState.ATTENDED, R.drawable.ic_attended, R.color.attended, R.string.attended),
    EXCUSED(AttendanceState.EXCUSED, R.drawable.ic_excused, R.color.accent_red, R.string.excused),
    LATE(AttendanceState.LATE, R.drawable.ic_late, R.color.accent_yellow, R.string.late);

    companion object {
        fun fromState(state: AttendanceState): Attendance {
            return values().find { it.model == state } ?: throw NoSuchElementException(state.name)
        }
    }

    fun apply(textView: TextView) {
        textView.setText(text)
        textView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        textView.setTextColor(color)
    }
}