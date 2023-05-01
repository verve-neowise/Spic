package com.neowise.spic.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neowise.spic.R
import com.neowise.spic.model.Person
import com.neowise.spic.util.loadImage
import de.hdodenhof.circleimageview.CircleImageView

class PersonAdapter(private val callback: (Person) -> Unit) : RecyclerView.Adapter<PersonAdapter.PlayerHolder>() {

    var items: List<Person> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_person, parent, false)
        return PlayerHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { callback(items[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PlayerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(person: Person) {

            val name = itemView.findViewById<TextView>(R.id.personName)
            val photo = itemView.findViewById<CircleImageView>(R.id.profilePhoto)

            name.text = person.name
            loadImage(itemView.context, person.id, photo)
        }
    }
}