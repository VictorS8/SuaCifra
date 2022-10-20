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
import br.com.suacifra.database.DatabaseHelper
import br.com.suacifra.databinding.AddNotesFragmentBinding
import br.com.suacifra.models.Notes
import br.com.suacifra.utils.Config
import br.com.suacifra.utils.getColorFromAttr

class AddNotesFragment : Fragment() {

    private lateinit var binding: AddNotesFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var noteTitle: String? = ""
    private var noteBody: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.add_notes_fragment, container, false)

        mainActivityContext = (activity as MainActivity)
        val args = this.arguments

        if (args != null) {
            noteTitle = args.getString(Config.NOTE_TITLE_BUNDLE_KEY)
            noteBody = args.getString(Config.NOTE_BODY_BUNDLE_KEY)
            binding.noteTitleEditText.setText(noteTitle)
            binding.noteBodyEditText.setText(noteBody)
            binding.deleteNoteButton.visibility = View.VISIBLE
        }

        binding.deleteNoteButton.setOnClickListener {
            if (args != null) {
                val databaseHelper = DatabaseHelper(mainActivityContext)
                val deleteStatus =
                    databaseHelper.deleteOneNote(args.getInt(Config.NOTE_ID_BUNDLE_KEY))
                if (deleteStatus > 0) {
                    mainActivityContext.toastMessage(
                        R.string.delete_note_successfully,
                        noteTitle ?: "",
                        Toast.LENGTH_LONG
                    )
                    mainActivityContext.replaceFragment(NotesFragment())
                } else {
                    mainActivityContext.toastMessage(
                        R.string.delete_note_failed,
                        Toast.LENGTH_LONG
                    )
                }
            }
        }

        val noteTitleEditText = binding.noteTitleEditText.text
        val noteBodyEditText = binding.noteBodyEditText.text

        binding.saveNoteButton.setOnClickListener {
            if (!noteTitleEditText.isNullOrBlank() && !noteBodyEditText.isNullOrBlank()) {
                val notesModel =
                    Notes(
                        -1,
                        noteTitleEditText.toString().trim(),
                        noteBodyEditText.toString().trim()
                    )
                val databaseHelper = DatabaseHelper(mainActivityContext)
                if (args != null) {
                    val updateStatus = databaseHelper.updateOneNote(notesModel)
                    if (updateStatus > 0) {
                        mainActivityContext.toastMessage(
                            R.string.edit_note_successfully,
                            noteTitleEditText.toString().trim(),
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
                    val insertStatus = databaseHelper.addOneNote(notesModel)
                    if (insertStatus) {
                        mainActivityContext.toastMessage(
                            R.string.add_note_successfully,
                            noteTitleEditText.toString().trim(),
                            Toast.LENGTH_LONG
                        )
                        mainActivityContext.replaceFragment(NotesFragment())
                    } else {
                        mainActivityContext.toastMessage(
                            R.string.save_note_missing_data,
                            Toast.LENGTH_LONG
                        )
                    }
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

        return binding.root

    }

}
