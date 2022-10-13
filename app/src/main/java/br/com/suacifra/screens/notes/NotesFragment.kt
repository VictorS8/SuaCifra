package br.com.suacifra.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.NotesFragmentBinding
import br.com.suacifra.models.Notes
import br.com.suacifra.screens.add.AddNotesFragment
import br.com.suacifra.screens.settings.SettingsFragment
import br.com.suacifra.viewModels.NotesRecycleViewAdapter

class NotesFragment : Fragment() {

    private lateinit var binding: NotesFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private lateinit var notesList: MutableList<Notes>
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: Adapter<NotesRecycleViewAdapter.ViewHolder>
    private lateinit var notesLayoutManager: LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.notes_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        // TODO - Change to update list
        notesList = fillNotesArray()

        recyclerView = binding.notesRecyclerView
        recyclerView.hasFixedSize()

        notesLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = notesLayoutManager

        notesAdapter = NotesRecycleViewAdapter(notesList, mainActivityContext)
        recyclerView.adapter = notesAdapter

        binding.addNotesImageButton.setOnClickListener {
            mainActivityContext.addToBackStackFragment(AddNotesFragment())
        }

        binding.notesGoBackButton.setOnClickListener {
            mainActivityContext.addToBackStackFragment(SettingsFragment())
        }

        return binding.root
    }

    private fun fillNotesArray(): MutableList<Notes> {
        val auxList: MutableList<Notes> = mutableListOf()
        val note0 = Notes(0, "Note 0", "Note Body 0")
        val note1 = Notes(1, "Note 1", "Note Body 1")
        val note2 = Notes(2, "Note 2", "Note Body 2")
        val note3 = Notes(3, "Note 3", "Note Body 3")
        val note4 = Notes(4, "Note 4", "Note Body 4")
        val note5 = Notes(5, "Note 5", "Note Body 5")
        val note6 = Notes(6, "Note 6", "Note Body 6")
        val note7 = Notes(7, "Note 7", "Note Body 7")
        val note8 = Notes(8, "Note 8", "Note Body 8")
        val note9 = Notes(9, "Note 9", "Note Body 9")

        auxList.addAll(
            mutableListOf(
                note0,
                note1,
                note2,
                note3,
                note4,
                note5,
                note6,
                note7,
                note8,
                note9
            )
        )

        return auxList
    }

}
