package com.neowise.spic.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neowise.spic.R
import com.neowise.spic.databinding.DialogChildOverviewBinding
import com.neowise.spic.model.Person
import com.neowise.spic.ui.activities.profiles.menu.Menu
import com.neowise.spic.ui.activities.schedules.startPlayerSchedule
import com.neowise.spic.ui.adapters.holder.MenuCallback
import com.neowise.spic.ui.adapters.holder.MenuListAdapter
import com.neowise.spic.util.loadImage
import com.neowise.spic.util.phoneCall
import com.neowise.spic.util.sendEmail

class PlayerOverviewDialog(val person: Person) : BottomSheetDialogFragment(), MenuCallback {

    private lateinit var binding: DialogChildOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogChildOverviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = MenuListAdapter(true, this)
        listAdapter.setItems(listOf(Menu.SCHEDULE, Menu.STATISTICS))

        binding.menuList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = listAdapter
        }

        binding.name.text = person.name

        loadImage(context!!, person.id, binding.profilePhoto)


        binding.phoneNumber.text = getString(R.string.phoneNumber) + ": " + person.phone
        binding.phoneNumber.setOnClickListener { phoneCall() }

        binding.emailAddress.text = getString(R.string.email) + ": " + person.email
        binding.emailAddress.setOnClickListener { sendEmail() }
    }

    private fun sendEmail() {
        context?.sendEmail(person.email)
    }

    private fun phoneCall() {
        context?.phoneCall(person.phone)
    }

    override fun onMenuSelected(menu: Menu) {
        when(menu) {
            Menu.SCHEDULE -> {
                context?.startPlayerSchedule(person.id)
            }
            Menu.STATISTICS -> {
                StatisticsDialog(person.id).show(fragmentManager!!, "")
            }
            else -> {}
        }
    }
}