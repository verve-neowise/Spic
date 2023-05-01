package com.neowise.spic.ui.activities.club

import android.content.Intent
import com.neowise.spic.Const
import com.neowise.spic.R
import com.neowise.spic.model.Person
import com.neowise.spic.Services
import com.neowise.spic.ui.activities.base.PersonListActivity
import com.neowise.spic.ui.adapters.holder.Action
import com.neowise.spic.ui.adapters.holder.ActionCallback
import com.neowise.spic.ui.dialogs.ActionDialog
import com.neowise.spic.ui.dialogs.ProfileOverviewDialog

class ParentsActivity : PersonListActivity(R.string.parents), ActionCallback {

    private var selectedPerson: Person? = null

    override fun initialize() {
        super.initialize()
        enableSearching(true)
    }

    override fun fetchData(): List<Person> {
        return Services.clubService.getParents()
    }

    override fun onPersonSelected(person: Person, position: Int) {
        selectedPerson = person

        ActionDialog(
            actions = listOf(Action.SHOW_CHILDREN, Action.PROFILE),
            callback = this
        ).show(supportFragmentManager, "tag")
    }

    override fun onAction(action: Action) {
        if (selectedPerson == null) return
        when(action) {
            Action.SHOW_CHILDREN -> {
                val intent = Intent(this, ParentChildrenActivity::class.java)
                intent.putExtra(Const.PARENT_ID, selectedPerson!!.id)
                startActivity(intent)
            }
            Action.PROFILE -> {
                ProfileOverviewDialog(selectedPerson!!).show(supportFragmentManager, "tag")
            }
        }
    }
}