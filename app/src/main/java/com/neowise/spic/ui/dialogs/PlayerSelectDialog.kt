package com.neowise.spic.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.neowise.spic.databinding.DialogPlayerListBinding
import com.neowise.spic.model.Person
import com.neowise.spic.ui.adapters.base.ListAdapter
import com.neowise.spic.ui.adapters.holder.PersonCallback
import com.neowise.spic.ui.adapters.holder.PersonListAdapter
import com.neowise.spic.util.RunIO

class PlayerSelectDialog(val playerProvider: () -> List<Person>, val callback: (Person) -> Unit)
    : DialogFragment(), SearchView.OnQueryTextListener, PersonCallback {

    private lateinit var binding: DialogPlayerListBinding
    private lateinit var personAdapter: ListAdapter<Person, PersonCallback>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPlayerListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personAdapter = PersonListAdapter(false, this)
        binding.searchView.setOnQueryTextListener(this)
        binding.personList.adapter = personAdapter
        binding.personList.layoutManager = LinearLayoutManager(context!!)
        loadPlayers()
    }

    private fun loadPlayers() {
        RunIO<List<Person>>()
            .run { playerProvider() }
            .onSuccess(personAdapter::setItems)
            .execute()
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            val query = it.trim()
            if (query.isNotEmpty()) {
                personAdapter.filter { it.name.contains(query, true) }
            }
            else {
                personAdapter.recoverFilter()
            }
        }
        return false
    }

    override fun onPersonSelected(person: Person, position: Int) {
        callback(person)
        dismiss()
    }
}