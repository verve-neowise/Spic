package com.neowise.spic.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neowise.spic.R
import com.neowise.spic.databinding.DialogTeamEditBinding
import com.neowise.spic.model.Team
import com.neowise.spic.util.buildModel
import com.neowise.spic.util.validateValue

class TeamEditDialog(val team: Team = Team.Empty) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogTeamEditBinding
    private var teamEditListener: TeamEditListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTeamEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveChangesButton.setOnClickListener {
            buildTeam().let {
                if (it != Team.Empty) {
                    teamEditListener?.onTeamEdited(it, if (team == Team.Empty) EditMethod.CREATE else EditMethod.EDIT)
                    dismiss()
                }
            }
        }
        if (team != Team.Empty) {
            binding.teamName.setText(team.name)
            binding.teamCodeEdit.setText(team.code)
            binding.activity.setText(team.activity)

            binding.title.setText(R.string.edit_team)
        }
        else {
            binding.teamName.setText("")
            binding.teamCodeEdit.setText("")
            binding.activity.setText("")

            binding.title.setText(R.string.new_team)
        }
    }

    private fun buildTeam(): Team {
        return buildModel(context!!, Team.Empty) {
            val code = binding.teamCodeEdit.validateValue("code")
            val name = binding.teamName.validateValue("name")
            val activity = binding.activity.validateValue("activity")

            return Team(team.id, code, name, activity)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TeamEditListener) {
            teamEditListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        teamEditListener = null
    }

    enum class EditMethod {
        CREATE,
        EDIT
    }

    interface TeamEditListener {
        fun onTeamEdited(team: Team, method: EditMethod)
    }
}