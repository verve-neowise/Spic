package com.neowise.spic.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neowise.spic.R
import com.neowise.spic.databinding.DialogStatisticsBinding
import com.neowise.spic.model.Statistics
import com.neowise.spic.Services
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO


class StatisticsDialog(private val personId: Int) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogStatisticsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.eventCount.text = ""
        binding.scores.text = ""
        binding.goods.text = ""
        binding.bads.text = ""
        binding.attended.text = ""
        binding.excused.text = ""
        binding.late.text = ""
        binding.workoutCount.text = ""
        loadStatistics()
    }

    private fun loadStatistics() {

        RunIO<Statistics>()
            .run { Services.profileService.getStatistics(personId) }
            .onSuccess(::fillEntries)
            .onFailure { errorToast(context!!, it) }
            .execute()
    }

    private fun fillEntries(statistics: Statistics) {
        binding.workoutCount.text = statistics.workouts.toString() + " " + getString(R.string.workouts)
        binding.eventCount.text = statistics.events.toString() + " " + getString(R.string.events)
        binding.scores.text = statistics.scores.toString()
        binding.goods.text = statistics.good.toString() + " " + getString(R.string.goods)
        binding.bads.text = statistics.bad.toString() + " " + getString(R.string.bads)
        binding.attended.text = statistics.attended.toString() + " " + getString(R.string.attended)
        binding.excused.text = statistics.excused.toString() + " " + getString(R.string.excused)
        binding.late.text = statistics.late.toString() + " " + getString(R.string.late)
    }
}