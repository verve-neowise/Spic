package com.neowise.spic.ui.dialogs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neowise.spic.Const
import com.neowise.spic.model.Role
import com.neowise.spic.databinding.DialogTeamOverviewBinding
import com.neowise.spic.model.Team
import com.neowise.spic.Preferences
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.AttendanceActivity
import com.neowise.spic.ui.activities.profiles.menu.Menu
import com.neowise.spic.ui.activities.schedules.TeamScheduleActivity
import com.neowise.spic.ui.activities.team.TeamPlayersActivity
import com.neowise.spic.ui.activities.team.TeamTrainersActivity
import com.neowise.spic.ui.adapters.holder.MenuCallback
import com.neowise.spic.ui.adapters.holder.MenuListAdapter
import com.neowise.spic.util.errorToast
import com.neowise.spic.util.RunIO

class TeamOverviewDialog(private val team: Team) : BottomSheetDialogFragment(), MenuCallback, TeamEditDialog.TeamEditListener {

    private lateinit var binding: DialogTeamOverviewBinding
    private var listener: TeamOverviewListener? = null

    private var currentTeam = team

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTeamOverviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = MenuListAdapter(true, this)
        listAdapter.setItems(buildMenuList())

        binding.name.text = team.name
        binding.teamCode.text = team.code
        binding.activity.text = ""

        binding.menuList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = listAdapter
        }
    }

    private fun buildMenuList(): List<Menu> {
        val token = Preferences.instance(context!!).token

        return if (token.role == Role.PLAYER) {
            listOf(Menu.SCHEDULE, Menu.PLAYERS, Menu.TRAINERS)
        }
        else if (token.role == Role.LEADER){
            listOf(Menu.SCHEDULE, Menu.PLAYERS, Menu.TRAINERS, Menu.ATTENDANCE, Menu.EDIT_TEAM, Menu.DELETE)
        }
        else {
            listOf(Menu.SCHEDULE, Menu.PLAYERS, Menu.TRAINERS, Menu.ATTENDANCE)
        }
    }

    override fun onMenuSelected(menu: Menu) {
        when(menu) {
            Menu.SCHEDULE -> {
                val intent = Intent(context, TeamScheduleActivity::class.java)
                intent.putExtra(Const.TEAM_ID, currentTeam.id)
                intent.putExtra(Const.TEAM_NAME, currentTeam.name)
                startActivity(intent)
            }
            Menu.PLAYERS -> {
                val intent = Intent(context, TeamPlayersActivity::class.java)
                intent.putExtra(Const.TEAM_ID, currentTeam.id)
                startActivity(intent)
            }
            Menu.TRAINERS -> {
                val intent = Intent(context, TeamTrainersActivity::class.java)
                intent.putExtra(Const.TEAM_ID, currentTeam.id)
                startActivity(intent)
            }
            Menu.ATTENDANCE -> {
                val intent = Intent(context, AttendanceActivity::class.java)
                intent.putExtra(Const.TEAM_ID, currentTeam.id)
                intent.putExtra(Const.TEAM_NAME, currentTeam.name)
                startActivity(intent)
            }
            Menu.EDIT_TEAM -> {
                TeamEditDialog(currentTeam).show(fragmentManager!!, "team edit")
            }
            Menu.DELETE -> {
                RunIO<Unit>()
                    .run { Services.teamService.removeTeam(currentTeam.id) }
                    .onSuccess {
                        listener?.teamRemoved(currentTeam)
                        Toast.makeText(context!!, "Team deleted!", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                    .onFailure { errorToast(context!!, it) }
                    .execute()
            }
            else -> {}
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TeamOverviewListener) {
            this.listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.listener = null
    }

    interface TeamOverviewListener {
        fun teamRemoved(team: Team)
    }

    override fun onTeamEdited(team: Team, method: TeamEditDialog.EditMethod) {
        this.currentTeam = team

        binding.name.text = team.name
        binding.teamCode.text = team.code
        binding.activity.text = ""
    }
}