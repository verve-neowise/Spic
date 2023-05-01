package com.neowise.spic.ui.adapters.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.neowise.spic.R
import com.neowise.spic.model.Role
import com.neowise.spic.ui.adapters.base.Holder
import com.neowise.spic.ui.adapters.base.HolderFactory
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.model.User
import com.neowise.spic.ui.adapters.base.Callback
import com.neowise.spic.util.loadImage
import de.hdodenhof.circleimageview.CircleImageView

class UsersAdapter(callback: UserCallback)
    : ListAdapter<User, UserCallback>(UserHolderFactory(), callback)

class UserHolderFactory : HolderFactory<User, UserCallback> {
    override fun createHolder(inflater: LayoutInflater, group: ViewGroup): Holder<User, UserCallback> {
        val view = inflater.inflate(R.layout.item_user, group, false)
        return UserHolder(view)
    }
}

class UserHolder(view: View) : Holder<User, UserCallback>(view) {

    override fun bind(value: User, callback: UserCallback) {
        val userName = itemView.findViewById<TextView>(R.id.userName)
        val details = itemView.findViewById<TextView>(R.id.userDetail)
        val photo = itemView.findViewById<CircleImageView>(R.id.profilePhoto)
        val crendetials = itemView.findViewById<TextView>(R.id.crindetials)

        userName.text = value.name
        crendetials.text = "@${value.username}  |  ${value.password}"
        details.setText(value.role.text)

        photo.borderWidth = 2

        val color  = when(value.role) {
            Role.NONE -> R.color.accent_red
            else -> R.color.accent
        }

        photo.borderColor = ContextCompat.getColor(itemView.context, color)
        details.setTextColor(ContextCompat.getColor(itemView.context, color))

        loadImage(itemView.context, value.id, photo)

        itemView.setOnClickListener { callback.userSelected(value)}
    }
}

interface UserCallback: Callback {
    fun userSelected(user: User)
}
