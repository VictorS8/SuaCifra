package br.com.suacifra.screens.notes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.database.DatabaseHelper
import br.com.suacifra.databinding.NotesFragmentBinding
import br.com.suacifra.models.Notes
import br.com.suacifra.screens.settings.SettingsFragment

class NotesFragment : Fragment() {

    private lateinit var binding: NotesFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private lateinit var notesList: MutableList<Notes>
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: Adapter<NotesRecyclerViewAdapter.ViewHolder>
    private lateinit var notesLayoutManager: LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.notes_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        val sharedPref = mainActivityContext.getSharedPreferences(
            getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE
        )

        val databaseHelper = DatabaseHelper(mainActivityContext)

        notesList = databaseHelper.getAllNotes()

        if (notesList.size == 0)
            binding.noNotesMessage.visibility = View.VISIBLE
        else
            binding.noNotesMessage.visibility = View.INVISIBLE

        recyclerView = binding.notesRecyclerView
        recyclerView.hasFixedSize()

        notesLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = notesLayoutManager

        notesAdapter = NotesRecyclerViewAdapter(notesList, mainActivityContext)
        recyclerView.adapter = notesAdapter

        binding.addNotesImageButton.setOnClickListener {
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putBoolean(
                getString(R.string.shared_preference_edit_notes_mode_boolean_key),
                false
            )
            sharedPrefEditor.apply()
            mainActivityContext.addToBackStackFragment(AddNotesFragment())
        }

        binding.notesGoBackButton.setOnClickListener {
            mainActivityContext.addToBackStackFragment(SettingsFragment())
        }

        return binding.root
    }

}
