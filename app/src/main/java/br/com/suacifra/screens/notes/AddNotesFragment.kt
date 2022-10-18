package br.com.suacifra.screens.notes

import android.content.Context
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

class AddNotesFragment : Fragment() {

    private lateinit var binding: AddNotesFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var isEditNotesModeEnable = false
    private var noteTitle: String? = ""
    private var noteBody: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.add_notes_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        val sharedPref = mainActivityContext.getSharedPreferences(
            getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE
        )

        isEditNotesModeEnable = sharedPref.getBoolean(
            getString(R.string.shared_preference_edit_notes_mode_boolean_key),
            isEditNotesModeEnable
        )

        if (isEditNotesModeEnable) {
            // if I clicked in one custom cifra
            noteTitle = sharedPref.getString(
                getString(R.string.shared_preference_edit_notes_mode_title_string_key),
                noteTitle
            )
            noteBody = sharedPref.getString(
                getString(R.string.shared_preference_edit_notes_mode_body_string_key),
                noteBody
            )
            binding.noteTitleEditText.setText(noteTitle ?: "")
            binding.noteBodyEditText.setText(noteBody ?: "")
        } else {
            // if I clicked on add bottom navigation option
            noteTitle = ""
            noteBody = ""
        }

        binding.cancelNoteImageButton.setOnClickListener {
            Toast.makeText(
                mainActivityContext,
                getString(R.string.add_note_cancelled),
                Toast.LENGTH_LONG
            )
                .show()
            mainActivityContext.replaceFragment(NotesFragment())
        }

        binding.addNoteImageButton.setOnClickListener {
            Toast.makeText(
                mainActivityContext,
                getString(R.string.add_note_successfully, binding.noteTitleEditText.text),
                Toast.LENGTH_LONG
            )
                .show()
            mainActivityContext.replaceFragment(NotesFragment())
        }

        return binding.root
    }

}
