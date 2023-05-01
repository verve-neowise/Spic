package com.neowise.spic.ui.adapters.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.neowise.spic.R
import com.neowise.spic.model.Person
import com.neowise.spic.ui.adapters.base.Callback
import com.neowise.spic.ui.adapters.base.Holder
import com.neowise.spic.ui.adapters.base.HolderFactory
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.util.loadImage
import de.hdodenhof.circleimageview.CircleImageView

fun PersonListAdapter(isCheckable: Boolean = false, callback: PersonCallback) : ListAdapter<Person, PersonCallback> {
    return ListAdapter(PersonHolderFactory(isCheckable), callback)
}

class PersonHolderFactory(private val isCheckable: Boolean = false) : HolderFactory<Person, PersonCallback> {

    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<Person, PersonCallback> {
        val view = inflater.inflate(R.layout.item_person_with_team, group, false)
        return PersonHolder(isCheckable, view)
    }
}

class PersonHolder(private val isCheckable: Boolean = false, view: View) : Holder<Person, PersonCallback>(view) {

    override fun bind(value: Person, callback: PersonCallback) {
        val name = itemView.findViewById<TextView>(R.id.personName)
        val photo = itemView.findViewById<CircleImageView>(R.id.profilePhoto)
        val team = itemView.findViewById<TextView>(R.id.teamName)
        val checkbox = itemView.findViewById<ImageView>(R.id.checkbox)

        name.text = value.name

        if (value.teamName != null && value.teamName.isNotEmpty()) {
            team.text = value.teamName
            team.visibility = View.VISIBLE
        }

        if (isCheckable) {
            checkbox.visibility = if (value.marked) View.VISIBLE else View.INVISIBLE
        }
        else {
            checkbox.visibility = View.INVISIBLE
        }

        loadImage(itemView.context, value.id, photo)

        itemView.setOnClickListener { callback.onPersonSelected(value, adapterPosition) }
    }
}

interface PersonCallback : Callback {
    fun onPersonSelected(person: Person, position: Int)
}