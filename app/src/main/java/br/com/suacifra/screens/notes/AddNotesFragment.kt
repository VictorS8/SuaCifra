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
import br.com.suacifra.database.DatabaseHelper
import br.com.suacifra.databinding.AddNotesFragmentBinding
import br.com.suacifra.models.Notes
import br.com.suacifra.utils.getColorFromAttr

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

        val noteTitleEditText = binding.noteTitleEditText.text
        val noteBodyEditText = binding.noteBodyEditText.text

        binding.addNoteImageButton.setOnClickListener {
            if (isEditNotesModeEnable) {
                val notesModel =
                    Notes(-1, noteTitleEditText.toString(), noteBodyEditText.toString())
                val databaseHelper = DatabaseHelper(mainActivityContext)
                val updateStatus = databaseHelper.updateOneNote(notesModel)
                if (updateStatus >= 0) {
                    mainActivityContext.toastMessage(
                        R.string.edit_note_successfully,
                        binding.noteTitleEditText.text,
                        Toast.LENGTH_LONG
                    )
                    mainActivityContext.replaceFragment(NotesFragment())
                } else {
                    mainActivityContext.toastMessage(
                        R.string.save_note_missing_data,
                        Toast.LENGTH_LONG
                    )
                }
            } else {
                if (!noteTitleEditText.isNullOrBlank() && !noteBodyEditText.isNullOrBlank()) {
                    val notesModel =
                        Notes(-1, noteTitleEditText.toString(), noteBodyEditText.toString())
                    val databaseHelper = DatabaseHelper(mainActivityContext)
                    val insertStatus = databaseHelper.addOneNote(notesModel)
                    if (insertStatus) {
                        mainActivityContext.toastMessage(
                            R.string.add_note_successfully,
                            binding.noteTitleEditText.text,
                            Toast.LENGTH_LONG
                        )
                        mainActivityContext.replaceFragment(NotesFragment())
                    } else {
                        mainActivityContext.toastMessage(
                            R.string.save_note_missing_data,
                            Toast.LENGTH_LONG
                        )
                    }
                } else if (noteTitleEditText.isNullOrBlank()) {
                    binding.noteTitleEditText.setHintTextColor(
                        mainActivityContext.getColorFromAttr(
                            com.google.android.material.R.attr.errorTextColor
                        )
                    )
                    mainActivityContext.toastMessage(
                        R.string.save_note_title_missing_data,
                        Toast.LENGTH_LONG
                    )
                } else if (noteBodyEditText.isNullOrBlank()) {
                    binding.noteBodyEditText.setHintTextColor(
                        mainActivityContext.getColorFromAttr(
                            com.google.android.material.R.attr.errorTextColor
                        )
                    )
                    mainActivityContext.toastMessage(
                        R.string.save_note_body_missing_data,
                        Toast.LENGTH_LONG
                    )
                } else {
                    mainActivityContext.toastMessage(
                        R.string.save_note_missing_data,
                        Toast.LENGTH_LONG
                    )
                }
            }
        }

        return binding.root
    }

}
