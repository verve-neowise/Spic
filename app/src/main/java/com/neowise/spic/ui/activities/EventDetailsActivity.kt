package com.neowise.spic.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.neowise.spic.Const
import com.neowise.spic.model.AttendanceState
import com.neowise.spic.model.EventDetail
import com.neowise.spic.model.StudentBehavior
import com.neowise.spic.databinding.ActivityEventDetailsBinding
import com.neowise.spic.Services
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.ui.adapters.holder.DetailAdapter
import com.neowise.spic.ui.adapters.holder.DetailCallback
import com.neowise.spic.ui.dialogs.DetailEditDialog
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class EventDetailsActivity : AppCompatActivity(), DetailCallback, DetailEditDialog.DetailEditListener {

    private lateinit var binding: ActivityEventDetailsBinding
    private lateinit var detailAdapter: ListAdapter<EventDetail, DetailCallback>
    private var scheduleId: Int = -1
    private var scheduleName: String = ""
    private var scheduleDateTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scheduleId = intent.getIntExtra(Const.SCHEDULE_ID, -1)
        scheduleName = intent.getStringExtra(Const.SCHEDULE_NAME)!!
        scheduleDateTime = intent.getStringExtra(Const.SCHEDULE_DATETIME)!!

        detailAdapter = DetailAdapter(this)

        setUpViews()

        binding.swipeRefresh.setOnRefreshListener {
            refreshData()
        }

        refreshData()
    }

    private fun refreshData() {
        binding.swipeRefresh.post { binding.swipeRefresh.isRefreshing = true }
        binding.detailList.visibility = View.INVISIBLE
        loadPlayers()
    }

    private fun setUpViews() {

        binding.eventName.text = scheduleName
        binding.eventDateTime.text = scheduleDateTime

        binding.detailList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailAdapter
        }

        binding.saveChangesButton.setOnClickListener {
            saveChanges()
            finish()
        }
    }

    private fun saveChanges() {
        val items = detailAdapter.getItems()

        RunIO<Unit>()
            .run { Services.scheduleService.updateScheduleDetails(items) }
            .onSuccess {
                Toast.makeText(this, "Details updated!", Toast.LENGTH_SHORT).show()
            }
            .onFailure { errorToast(this, it) }
            .execute()
    }

    private fun loadPlayers() {
        RunIO<List<EventDetail>>()
            .run {
                Services.scheduleService.getScheduleDetails(scheduleId)
            }
            .onSuccess {
                detailAdapter.setItems(it)
                binding.detailList.visibility = View.VISIBLE
                binding.swipeRefresh.isRefreshing = false
            }
            .onFailure { errorToast(this, it) }
            .execute()
    }

    override fun onDetailEdited(detail: EventDetail, state: AttendanceState, behavior: StudentBehavior) {

        val newDetail = detail.copy(state = state, behavior = behavior)
        detailAdapter.changeItem(detail, newDetail)

        RunIO<Unit>()
            .run { Services.scheduleService.updateScheduleDetail(newDetail) }
            .onSuccess {
                detailAdapter.changeItem(detail, newDetail)
            }
            .onFailure { errorToast(this, it) }
            .execute()
    }

    override fun ondetailSelected(detail: EventDetail) {
        supportFragmentManager.let {
            DetailEditDialog(detail).show(it, "details")
        }
    }
}