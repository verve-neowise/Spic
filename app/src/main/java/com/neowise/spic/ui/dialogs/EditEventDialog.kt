package com.neowise.spic.ui.dialogs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neowise.spic.model.AttendanceState
import com.neowise.spic.model.EventType
import com.neowise.spic.model.StudentBehavior
import com.neowise.spic.databinding.GialogAddEventBinding
import com.neowise.spic.model.Schedule
import com.neowise.spic.model.Team
import com.neowise.spic.Services
import com.neowise.spic.ui.adapters.holder.CustomSpinnerAdapter
import com.neowise.spic.ui.dialogs.TeamEditDialog.EditMethod
import com.neowise.spic.ui.dialogs.TeamEditDialog.EditMethod.*
import com.neowise.spic.util.*
import com.neowise.spic.util.RunIO

class EditEventDialog(val trainerId: Int, val editable: Schedule = Schedule.Empty, val selectedTeam: Team = Team.Empty) : BottomSheetDialogFragment() {

    private lateinit var binding: GialogAddEventBinding
    private var eventDialogListener: EventDialogListener? = null
    private lateinit var spinnerAdapter: CustomSpinnerAdapter<Team>
    private lateinit var typeAdapter: CustomSpinnerAdapter<EventType>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = GialogAddEventBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveChangesButton.setOnClickListener {
            val schedule = getScheduleModel()
            if (schedule != Schedule.Empty) {
                eventDialogListener?.onEventEdit(schedule, if (editable == Schedule.Empty) CREATE else EDIT)
                dismiss()
            }
        }

        typeAdapter = CustomSpinnerAdapter(context!!, listOf(EventType.WORKOUT, EventType.EVENT))
        binding.typeListSpinner.adapter = typeAdapter
        binding.typeListSpinner.setSelection(0)

        val now = SimpleDate.now()

        binding.startTime.setOnClickListener {
            TimePickerDialog(context!!, { view, hourOfDay, minute ->
                binding.startTime.setText(SimpleTime(hourOfDay, minute).toString())
            }, 0, 0, true).show()
        }

        binding.endTime.setOnClickListener {
            TimePickerDialog(context!!, { view, hourOfDay, minute ->
                binding.endTime.setText(SimpleTime(hourOfDay, minute).toString())
            }, 0, 0, true).show()
        }

        binding.date.setOnClickListener {
            DatePickerDialog(context!!,
                { _, year, month, dayOfMonth ->
                    binding.date.setText(SimpleDate(year, month+1, dayOfMonth).toString())
                },
                now.year, now.month-1, now.day).show()
        }
        binding.startTime.readOnly()
        binding.endTime.readOnly()
        binding.date.readOnly()

        if (editable != Schedule.Empty) {
            binding.nameEdit.setText(editable.name)
            binding.description.setText(editable.description)
            binding.date.setText(editable.date.toString())
            binding.startTime.setText(editable.time.start.toString())
            binding.endTime.setText(editable.time.end.toString())
            binding.typeListSpinner.setSelection(if (editable.type == EventType.WORKOUT) 0 else 1)
        }

        loadTeams()
    }

    private fun loadTeams() {

        RunIO<List<Team>>()
            .run { Services.teamService.getTeamsByTrainer(trainerId) }
            .onSuccess(::setItems)
            .onFailure { errorToast(context!!, it) }
            .execute()
    }

    private fun setItems(teams: List<Team>) {

        spinnerAdapter = CustomSpinnerAdapter(context!!, teams)
        binding.teamListSpinner.adapter = spinnerAdapter
        binding.teamListSpinner.setSelection(0)

        if (editable != Schedule.Empty) {
            teams.find { it.id == editable.teamId }?.let {
                binding.teamListSpinner.setSelection(spinnerAdapter.getPosition(it))
            }
        }

        if (selectedTeam != Team.Empty) {
            teams.find { team -> team.id == selectedTeam.id }.let {
                binding.teamListSpinner.setSelection(spinnerAdapter.getPosition(it))
            }
        }
    }

    private fun getScheduleModel(): Schedule {
        return buildModel(context!!, Schedule.Empty) {
            val name = binding.nameEdit.validateValue("name")
            val date = binding.date.validateValue("date")
            val startTime = binding.startTime.validateValue("startTime")
            val endTime = binding.endTime.validateValue("endTime")
            val description = binding.description.validateValue("description")
            val team = if (binding.teamListSpinner.selectedItemPosition != -1) {
                binding.teamListSpinner.selectedItem as Team
            }
            else {
                Team(-1, "", "", "")
            }
            val type = binding.typeListSpinner.selectedItem as EventType

            val timeRange = TimeRange(SimpleTime.parse(startTime), SimpleTime.parse(endTime))
            val id = if (editable != Schedule.Empty) editable.id else -1

            Schedule(
                id = id,
                teamId = team.id,
                teamName = team.name,
                name = name,
                description = description,
                trainers = "",
                date = SimpleDate.parse(date),
                time = timeRange,
                attendance = AttendanceState.ATTENDED,
                behavior = StudentBehavior.POSITIVELY,
                type = type
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EventDialogListener) {
            eventDialogListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        eventDialogListener = null
    }

    interface EventDialogListener {
        fun onEventEdit(schedule: Schedule, mode: EditMethod)
    }
}