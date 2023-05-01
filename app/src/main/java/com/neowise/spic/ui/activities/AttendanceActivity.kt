package com.neowise.spic.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.neowise.spic.R
import com.neowise.spic.Const
import com.neowise.spic.model.PlayerAttendance
import com.neowise.spic.databinding.ActivityAttendanceBinding
import com.neowise.spic.model.TeamAttendance
import com.neowise.spic.model.Token
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.adapters.base.Empty
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.ui.adapters.holder.AttendanceAdapter
import com.neowise.spic.ui.adapters.holder.CustomSpinnerAdapter
import com.neowise.spic.util.Month
import com.neowise.spic.util.SimpleDate
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class AttendanceActivity : AppCompatActivity() {

    lateinit var binding: ActivityAttendanceBinding
    lateinit var attendanceAdapter: ListAdapter<PlayerAttendance, Empty>

    lateinit var yearSpinnerAdapter: CustomSpinnerAdapter<Int>
    lateinit var monthSpinnerAdapter: CustomSpinnerAdapter<Month>

    lateinit var teamName: String
    var teamId: Int = 0

    lateinit var token: Token

    private var years = listOf<Int>()
    private var months = listOf<Month>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        teamId = intent.getIntExtra(Const.TEAM_ID, 0)
        teamName = intent.getStringExtra(Const.TEAM_NAME)!!

        token = Preferences.instance(this).token

        attendanceAdapter = AttendanceAdapter()

        binding.teamName.text = teamName

        binding.searchButton.setOnClickListener {
            refreshData()
        }

        binding.attendanceList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = attendanceAdapter
        }

        binding.swipeRefresh.setOnRefreshListener {
            refreshData()
        }
        setupSpinners()
        refreshData()
    }

    private fun refreshData() {
        binding.swipeRefresh.post { binding.swipeRefresh.isRefreshing = true }
        binding.container.visibility = View.INVISIBLE
        fetch()
    }

    private fun setupSpinners() {
        val now = SimpleDate.now()
        years = (2018..now.year).toList()
        yearSpinnerAdapter = CustomSpinnerAdapter<Int>(this, years)

        binding.yearSpinner.adapter = yearSpinnerAdapter
        binding.yearSpinner.setSelection(years.size-1)

        months = Month.values().toList()
        monthSpinnerAdapter = CustomSpinnerAdapter<Month>(this, months)
        binding.monthSpinner.adapter = monthSpinnerAdapter
        binding.monthSpinner.setSelection(now.month-1)
    }

    private fun fetch() {

        val year: Int = years[binding.yearSpinner.selectedItemPosition]
        val month: Month = months[binding.monthSpinner.selectedItemPosition]

        val date = SimpleDate(year, month.ordinal+1, 1)

        RunIO<TeamAttendance>()
            .run {
                Services.teamService.getAttendance(teamId, date)
            }
            .onSuccess {
                buildInfoLayout(it.dates, it.attendances.size)
                attendanceAdapter.setItems(it.attendances)
                binding.container.visibility = View.VISIBLE
                binding.swipeRefresh.isRefreshing = false
            }
            .onFailure {
                errorToast(this, it)
            }
            .execute()
    }

    private fun buildInfoLayout(dates: List<Int>, count: Int) {
        binding.playersCount.text = "$count " + getString(R.string.players)
        binding.infoLayout.removeAllViewsInLayout()

        for (date in dates) {
            val dateView = layoutInflater.inflate(R.layout.view_date, binding.infoLayout, false) as TextView
            dateView.text = date.toString()
            binding.infoLayout.addView(dateView)
        }
    }
}