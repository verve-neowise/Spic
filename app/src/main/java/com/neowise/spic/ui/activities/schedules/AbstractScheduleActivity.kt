package com.neowise.spic.ui.activities.schedules

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.neowise.spic.model.EventType
import com.neowise.spic.databinding.ActivityScheduleBinding
import com.neowise.spic.model.Schedule
import com.neowise.spic.ui.adapters.base.Callback
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.ui.adapters.holder.CustomWhiteAdapter
import com.neowise.spic.util.*

abstract class AbstractScheduleActivity : AppCompatActivity() {

    protected lateinit var binding: ActivityScheduleBinding
    private lateinit var scheduleAdapter: ListAdapter<Schedule, out Callback>
    private lateinit var spinnerAdapter: CustomWhiteAdapter<EventType>

    private var startDate: SimpleDate? = null
    private var endDate: SimpleDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startDate = SimpleDate.now()
        endDate = startDate!!.addDay(7)

        binding.startDate.setText(startDate!!.toString())
        binding.endDate.setText(endDate!!.toString())
        binding.startDate.readOnly()
        binding.endDate.readOnly()

        binding.searchButton.setOnClickListener {
            fetchData()
        }

        binding.addEventButton.setOnClickListener {
            onAddEventClicked()
        }

        spinnerAdapter = CustomWhiteAdapter<EventType>(this, listOf(*EventType.values()))
        binding.typeSpinner.adapter = spinnerAdapter

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fetchData()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.typeSpinner.setSelection(0)

        binding.swipeRefresh.setOnRefreshListener {
            fetchData()
        }

        binding.startDate.setOnClickListener {
            DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    binding.startDate.setText(SimpleDate(year, month+1, dayOfMonth).toString())
                },
                startDate!!.year, startDate!!.month-1, startDate!!.day).show()
        }

        binding.endDate.setOnClickListener {
            DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    binding.endDate.setText(SimpleDate(year, month+1, dayOfMonth).toString())
                },
                endDate!!.year, endDate!!.month-1, endDate!!.day).show()
        }

        initialize()
        fetchData()
    }

    abstract fun initialize()

    protected fun fetchData() {
        binding.scheduleList.visibility = View.INVISIBLE
        binding.swipeRefresh.post { binding.swipeRefresh.isRefreshing = true }
        fetchData(dateRange(), getEventType())
    }

    abstract fun fetchData(dateRange: DateRange, eventType: EventType)

    protected open fun onAddEventClicked() {}

    protected fun adapter(scheduleAdapter: ListAdapter<Schedule, out Callback>){
        this.scheduleAdapter = scheduleAdapter

        binding.scheduleList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = scheduleAdapter
        }
    }

    protected fun setItems(items: List<Schedule>) {
        scheduleAdapter.setItems(items)
        binding.scheduleList.visibility = View.VISIBLE
        binding.swipeRefresh.isRefreshing = false
    }

    protected fun dateRange() : DateRange {

        return buildModel(this, emptyDateRange()) {
            val startDate = binding.startDate.validateValue("start date")
            val endDate = binding.endDate.validateValue("end date")

            return DateRange(SimpleDate.parse(startDate), SimpleDate.parse(endDate))
        }
    }

    private fun emptyDateRange(): DateRange {
        return DateRange(SimpleDate.now(), SimpleDate.now())
    }

    private fun getEventType(): EventType {
        return spinnerAdapter.getItem(binding.typeSpinner.selectedItemPosition) ?: EventType.ALL
    }
}