package br.com.suacifra.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.AddNotesFragmentBinding
import br.com.suacifra.screens.notes.NotesFragment

class AddNotesFragment : Fragment() {

    private lateinit var binding: AddNotesFragmentBinding
    private lateinit var mainActivityContext: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.add_notes_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        binding.cancelNoteImageButton.setOnClickListener {
            mainActivityContext.replaceFragment(NotesFragment())
        }

        binding.addNoteImageButton.setOnClickListener {

            Toast.makeText(
                mainActivityContext,
                getString(R.string.add_note_successfully),
                Toast.LENGTH_LONG
            )
                .show()
            mainActivityContext.replaceFragment(NotesFragment())
        }

        return binding.root
    }

}
