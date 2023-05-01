package com.neowise.spic.ui.adapters.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.neowise.spic.R
import com.neowise.spic.model.AttendanceState
import com.neowise.spic.model.EventType
import com.neowise.spic.model.Schedule
import com.neowise.spic.model.StudentBehavior
import com.neowise.spic.ui.Attendance
import com.neowise.spic.ui.Behaviour
import com.neowise.spic.ui.adapters.base.Callback
import com.neowise.spic.ui.adapters.base.Empty
import com.neowise.spic.ui.adapters.base.Holder
import com.neowise.spic.ui.adapters.base.HolderFactory
import com.neowise.spic.util.monthName

class ScheduleHolderFactory : HolderFactory<Schedule, ScheduleCallback> {
    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<Schedule, ScheduleCallback> {
        val view = inflater.inflate(R.layout.item_schedule, group, false)
        return ScheduleHolder(view)
    }
}

class PlayerScheduleHolderFactory : HolderFactory<Schedule, Empty> {
    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<Schedule, Empty> {
        val view = inflater.inflate(R.layout.item_schedule, group, false)
        return PlayerScheduleHolder(view)
    }
}

interface ScheduleCallback : Callback {
    fun onScheduleSelected(schedule: Schedule)
}

class ScheduleHolder(view: View) : Holder<Schedule, ScheduleCallback>(view) {
    override fun bind(value: Schedule, callback: ScheduleCallback) {

        val eventDay = itemView.findViewById<TextView>(R.id.eventDay)
        val eventMonth = itemView.findViewById<TextView>(R.id.eventMonth)
        val name = itemView.findViewById<TextView>(R.id.name)
        val desc = itemView.findViewById<TextView>(R.id.description)
        val trainerList = itemView.findViewById<TextView>(R.id.trainerList)
        val attendanceView = itemView.findViewById<TextView>(R.id.attendance)
        val behaviourView = itemView.findViewById<TextView>(R.id.behaviour)
        val startTime = itemView.findViewById<TextView>(R.id.startTime)
        val endTime = itemView.findViewById<TextView>(R.id.endTime)
        val typeSymbol = itemView.findViewById<TextView>(R.id.typeName)

        name.text = "${value.teamName} | ${value.name}"

        eventDay.text = value.date.day.toString()
        eventMonth.text = value.date.month.monthName()

        startTime.text = value.time.start.toString()
        endTime.text = value.time.end.toString()

        trainerList.text = value.trainers

        if (value.type == EventType.WORKOUT) {
            typeSymbol.text = "W"
            typeSymbol.background = ContextCompat.getDrawable(itemView.context, R.drawable.workout_bg)
        }
        else {
            typeSymbol.text = "E"
            typeSymbol.background = ContextCompat.getDrawable(itemView.context, R.drawable.event_bg)
        }

        if (value.description.isNullOrEmpty()) {
            desc.visibility = View.GONE
        }
        else {
            desc.text = value.description
        }

        attendanceView.visibility = View.GONE
        behaviourView.visibility = View.GONE

        itemView.setOnClickListener { callback.onScheduleSelected(value) }
    }
}

class PlayerScheduleHolder(view: View) : Holder<Schedule, Empty>(view) {

    override fun bind(value: Schedule, callback: Empty) {

        val eventDay = itemView.findViewById<TextView>(R.id.eventDay)
        val eventMonth = itemView.findViewById<TextView>(R.id.eventMonth)
        val desc = itemView.findViewById<TextView>(R.id.description)
        val name = itemView.findViewById<TextView>(R.id.name)
        val trainerList = itemView.findViewById<TextView>(R.id.trainerList)
        val attendanceView = itemView.findViewById<TextView>(R.id.attendance)
        val behaviourView = itemView.findViewById<TextView>(R.id.behaviour)
        val startTime = itemView.findViewById<TextView>(R.id.startTime)
        val endTime = itemView.findViewById<TextView>(R.id.endTime)
        val typeSymbol = itemView.findViewById<TextView>(R.id.typeName)

        name.text = "${value.teamName} | ${value.name}"

        eventDay.text = value.date.day.toString()
        eventMonth.text = value.date.month.monthName()

        startTime.text = value.time.start.toString()
        endTime.text = value.time.end.toString()

        trainerList.text = value.trainers
        
        if (value.type == EventType.WORKOUT) {
            typeSymbol.text = "W"
            typeSymbol.background = ContextCompat.getDrawable(itemView.context, R.drawable.workout_bg)
        }
        else {
            typeSymbol.text = "E"
            typeSymbol.background = ContextCompat.getDrawable(itemView.context, R.drawable.event_bg)
        }

        if (value.description.isNullOrEmpty()) {
            desc.visibility = View.GONE
        }
        else {
            desc.text = value.description
        }

        if (value.attendance == AttendanceState.NONE) {
            attendanceView.visibility = View.INVISIBLE
        }
        else {
            val attendance = Attendance.fromState(value.attendance)
            attendanceView.setText(attendance.text)
            attendanceView.setCompoundDrawablesWithIntrinsicBounds(attendance.icon, 0, 0, 0)
            attendanceView.setTextColor(attendance.color)
        }

        if (value.behavior == StudentBehavior.NONE) {
            behaviourView.visibility = View.INVISIBLE
        }
        else {
            val behavior = Behaviour.fromState(value.behavior)
            behaviourView.setText(behavior.text)
            behaviourView.setCompoundDrawablesWithIntrinsicBounds(behavior.icon, 0, 0, 0)
            behaviourView.setTextColor(behavior.color)
        }
    }
}