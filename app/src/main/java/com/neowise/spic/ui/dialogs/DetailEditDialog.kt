package com.neowise.spic.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neowise.spic.R
import com.neowise.spic.model.AttendanceState
import com.neowise.spic.model.EventDetail
import com.neowise.spic.model.StudentBehavior
import com.neowise.spic.databinding.DialogAttendanceBinding
import com.neowise.spic.util.loadImage

class DetailEditDialog(val detail: EventDetail) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogAttendanceBinding
    private var detailEditListener: DetailEditListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAttendanceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener {
            val state = getState()
            val behavior = getBehavior()
            if (state == AttendanceState.NONE || behavior == StudentBehavior.NONE) {
                Toast.makeText(context!!, "entries must be filled!", Toast.LENGTH_SHORT).show()
            }
            detailEditListener?.onDetailEdited(detail, getState(), getBehavior())
            dismiss()
        }

        binding.name.text = detail.name

        loadImage(context!!, detail.photoUrl, binding.circleImageView)

        binding.stateGroup.check(when(detail.state) {
            AttendanceState.LATE -> R.id.late
            AttendanceState.EXCUSED -> R.id.excused
            AttendanceState.ATTENDED -> R.id.attended
            AttendanceState.NONE -> -1
        })

        binding.behaviorGroup.check(when(detail.behavior) {
            StudentBehavior.POSITIVELY -> R.id.good
            StudentBehavior.BAD -> R.id.bad
            StudentBehavior.NONE -> -1
        })
    }

    private fun getBehavior(): StudentBehavior {
        return when(binding.behaviorGroup.checkedRadioButtonId) {
            R.id.good -> StudentBehavior.POSITIVELY
            R.id.bad -> StudentBehavior.BAD
            -1 -> StudentBehavior.NONE
            else -> throw RuntimeException("no selection")
        }
    }

    private fun getState(): AttendanceState {
        return when(binding.stateGroup.checkedRadioButtonId) {
            R.id.attended -> AttendanceState.ATTENDED
            R.id.excused -> AttendanceState.EXCUSED
            R.id.late -> AttendanceState.LATE
            -1 -> AttendanceState.NONE
            else -> throw RuntimeException("no selection")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DetailEditListener) {
            detailEditListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        detailEditListener = null
    }

    interface DetailEditListener {
        fun onDetailEdited(detail: EventDetail, state: AttendanceState, behavior: StudentBehavior)
    }
}