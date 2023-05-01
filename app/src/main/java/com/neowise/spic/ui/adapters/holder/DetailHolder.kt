package com.neowise.spic.ui.adapters.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.neowise.spic.R
import com.neowise.spic.model.EventDetail
import com.neowise.spic.ui.Attendance
import com.neowise.spic.ui.Behaviour
import com.neowise.spic.ui.adapters.base.Callback
import com.neowise.spic.ui.adapters.base.Holder
import com.neowise.spic.ui.adapters.base.HolderFactory
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.model.AttendanceState
import com.neowise.spic.model.StudentBehavior
import com.neowise.spic.util.loadImage

fun DetailAdapter(callback: DetailCallback): ListAdapter<EventDetail, DetailCallback> {
    return ListAdapter(DetailHolderFactory(), callback)
}

class DetailHolderFactory : HolderFactory<EventDetail, DetailCallback> {

    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<EventDetail, DetailCallback> {
        val view = inflater.inflate(R.layout.item_event_detail, group, false)
        return DetailHolder(view)
    }
}

class DetailHolder(view: View) : Holder<EventDetail, DetailCallback>(view) {

    override fun bind(value: EventDetail, callback: DetailCallback) {

        val photo = itemView.findViewById<ImageView>(R.id.circleImageView)
        val name = itemView.findViewById<TextView>(R.id.name)
        val attendanceView = itemView.findViewById<TextView>(R.id.state)
        val behaviourView = itemView.findViewById<TextView>(R.id.behaviour)

        name.text = value.name

        if (value.state == AttendanceState.NONE) {
            attendanceView.visibility = View.INVISIBLE
        }
        else {
            val attendance = Attendance.fromState(value.state)

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
        loadImage(itemView.context, value.id, photo)

        itemView.setOnClickListener { callback.ondetailSelected(value) }
    }
}

interface DetailCallback : Callback {
    fun ondetailSelected(detail: EventDetail)
}