package com.neowise.spic.ui.adapters.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.neowise.spic.R
import com.neowise.spic.model.AttendanceState
import com.neowise.spic.model.PlayerAttendance
import com.neowise.spic.ui.Attendance
import com.neowise.spic.ui.adapters.base.Empty
import com.neowise.spic.ui.adapters.base.Holder
import com.neowise.spic.ui.adapters.base.HolderFactory
import com.neowise.spic.ui.adapters.base.ListAdapter

fun AttendanceAdapter(): ListAdapter<PlayerAttendance, Empty> {
    return ListAdapter(AttendanceHolderFactory(), Empty)
}

class AttendanceHolderFactory : HolderFactory<PlayerAttendance, Empty> {

    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<PlayerAttendance, Empty> {
        return AttendanceHolder(inflater.inflate(R.layout.item_attendance, group, false))
    }
}

class AttendanceHolder(view: View) : Holder<PlayerAttendance, Empty>(view) {

    override fun bind(value: PlayerAttendance, callback: Empty) {

        val name = itemView.findViewById<TextView>(R.id.name)
        val attendedCount = itemView.findViewById<TextView>(R.id.attendedCount)
        val lateCount = itemView.findViewById<TextView>(R.id.lateCount)
        val excusedCount = itemView.findViewById<TextView>(R.id.excusedCount)
        val percent = itemView.findViewById<TextView>(R.id.percent)

        val layout = itemView.findViewById<LinearLayout>(R.id.attendanceLayout)

        name.text = value.name
        attendedCount.text = value.attendedCount.toString()
        lateCount.text = value.lateCount.toString()
        excusedCount.text = value.excusedCount.toString()
        percent.text = value.percent.toString() + "%"

        layout.removeAllViewsInLayout()

        for (state in value.states) {

            val imageView = LayoutInflater.from(itemView.context)
                .inflate(R.layout.view_attendance, layout, false) as ImageView

            if (state == AttendanceState.NONE) {
                imageView.setImageResource(R.drawable.ic_none)
            }
            else {
                imageView.setImageResource(Attendance.fromState(state).icon)
            }
            layout.addView(imageView)
        }
    }
}