package br.com.suacifra.screens.add

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
import br.com.suacifra.databinding.AddSequenceFragmentBinding
import br.com.suacifra.screens.notes.NotesFragment
import br.com.suacifra.utils.addStringAt
import br.com.suacifra.utils.getColorFromAttr
import br.com.suacifra.utils.mutableSetToString
import br.com.suacifra.utils.stringOfMutableListToEditTextString

class AddSequenceFragment : Fragment() {

    private lateinit var binding: AddSequenceFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var isEditSequenceModeEnable = false
    private var cifraId: Int = 0
    private var sequence: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.add_sequence_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        val sharedPref = mainActivityContext.getSharedPreferences(
            getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE
        )

        isEditSequenceModeEnable = sharedPref.getBoolean(
            getString(R.string.shared_preference_edit_sequence_mode_boolean_key),
            isEditSequenceModeEnable
        )

        if (isEditSequenceModeEnable) {
            // if I clicked in one custom cifra
            cifraId = sharedPref.getInt(
                getString(R.string.shared_preference_edit_cifra_mode_id_int_key),
                cifraId
            )
            sequence = sharedPref.getString(
                getString(R.string.shared_preference_edit_sequence_mode_sequence_string_key),
                sequence
            )
            binding.sequenceEditText.setText(stringOfMutableListToEditTextString(sequence ?: ""))
            binding.deleteSequenceButton.visibility = View.VISIBLE
        } else {
            // if I clicked on add bottom navigation option
            cifraId = -1
            sequence = ""
            binding.deleteSequenceButton.visibility = View.GONE
        }

        binding.deleteSequenceButton.setOnClickListener {
            var deleteSequence: MutableSet<String>? = mutableSetOf()
            deleteSequence = sharedPref.getStringSet(
                mainActivityContext.getString(R.string.shared_preference_edit_cifra_mode_sequence_set_string_key),
                deleteSequence
            )

            val sequenceToDelete = sequence

            deleteSequence?.remove(sequenceToDelete)

            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putStringSet(
                mainActivityContext.getString(R.string.shared_preference_edit_cifra_mode_sequence_set_string_key),
                deleteSequence
            )
            sharedPrefEditor.apply()
            Toast.makeText(
                mainActivityContext,
                getString(R.string.add_note_cancelled),
                Toast.LENGTH_LONG
            )
                .show()
            mainActivityContext.replaceFragment(AddFragment())
        }

        binding.saveSequenceButton.setOnClickListener {
            val sequenceEditText = binding.sequenceEditText.text
            if (!sequenceEditText.isNullOrBlank()) {
                if (isEditSequenceModeEnable) {
                    var mutableSetOfString: MutableSet<String>? = mutableSetOf()
                    var editSequenceIndex = 0

                    mutableSetOfString = sharedPref.getStringSet(
                        getString(R.string.shared_preference_edit_cifra_mode_sequence_set_string_key),
                        mutableSetOfString
                    )
                    editSequenceIndex = sharedPref.getInt(
                        getString(R.string.shared_preference_edit_sequence_mode_sequence_index_key),
                        editSequenceIndex
                    )

                    val newMutableSetOfString = mutableSetOfString?.addStringAt(
                        editSequenceIndex,
                        sequenceEditText.toString().trim()
                    )
                    val sharedPrefEditor = sharedPref.edit()
                    sharedPrefEditor.putStringSet(
                        getString(R.string.shared_preference_edit_cifra_mode_sequence_set_string_key),
                        newMutableSetOfString
                    )
                    sharedPrefEditor.apply()
                    Toast.makeText(
                        mainActivityContext,
                        getString(R.string.edit_sequence_successfully),
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    var mutableSetOfString: MutableSet<String>? = mutableSetOf()
                    mutableSetOfString = sharedPref.getStringSet(
                        getString(R.string.shared_preference_edit_cifra_mode_sequence_set_string_key),
                        mutableSetOfString
                    )

                    val newMutableListOfString = mutableSetOfString?.toMutableList()
                    newMutableListOfString?.add(sequenceEditText.toString().trim())
                    newMutableListOfString?.toMutableSet()
                    val newMutableSetOfString = newMutableListOfString?.toMutableSet()
                    val sharedPrefEditor = sharedPref.edit()
                    sharedPrefEditor.putStringSet(
                        getString(R.string.shared_preference_edit_cifra_mode_sequence_set_string_key),
                        newMutableSetOfString
                    )
                    sharedPrefEditor.apply()
                    Toast.makeText(
                        mainActivityContext,
                        getString(R.string.add_sequence_successfully),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                mainActivityContext.replaceFragment(AddFragment())
            } else {
                binding.sequenceEditText.setHintTextColor(
                    mainActivityContext.getColorFromAttr(
                        com.google.android.material.R.attr.errorTextColor
                    )
                )
                mainActivityContext.toastMessage(
                    R.string.save_sequence_missing_data,
                    Toast.LENGTH_LONG
                )
            }
        }

        return binding.root
    }

}
