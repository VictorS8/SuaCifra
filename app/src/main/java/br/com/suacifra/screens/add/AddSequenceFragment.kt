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
import br.com.suacifra.databinding.AddSequenceFragmentBinding
import br.com.suacifra.utils.*

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
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )

        isEditSequenceModeEnable = sharedPref.getBoolean(
            Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_BOOLEAN_KEY,
            isEditSequenceModeEnable
        )

        if (isEditSequenceModeEnable) {
            // if I clicked in one custom cifra
            cifraId = sharedPref.getInt(
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_ID_INT_KEY,
                cifraId
            )
            sequence = sharedPref.getString(
                Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_SEQUENCE_STRING_KEY,
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
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                deleteSequence
            )

            val sequenceToDelete = sequence

            deleteSequence?.remove(sequenceToDelete)

            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putStringSet(
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
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
                var mutableSetOfString: MutableSet<String> = mutableSetOf()
                if (isEditSequenceModeEnable) {
                    var editSequenceIndex = 0

                    mutableSetOfString = sharedPref.getStringSet(
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                        mutableSetOfString
                    ) ?: mutableSetOf()
                    editSequenceIndex = sharedPref.getInt(
                        Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_SEQUENCE_INDEX_KEY,
                        editSequenceIndex
                    )

                    val newMutableSetOfString = mutableSetOfString?.addStringAt(
                        editSequenceIndex,
                        sequenceEditText.toString().trim()
                    )
                    val sharedPrefEditor = sharedPref.edit()
                    sharedPrefEditor.putStringSet(
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
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
                    mutableSetOfString = sharedPref.getStringSet(
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                        mutableSetOfString
                    ) ?: mutableSetOf()

                    val newMutableListOfString = mutableSetOfString.toMutableList()
                    newMutableListOfString.add(sequenceEditText.toString().trim())
                    newMutableListOfString.toMutableSet()
                    val newMutableSetOfString = newMutableListOfString.toMutableSet()
                    val sharedPrefEditor = sharedPref.edit()
                    sharedPrefEditor.putStringSet(
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
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
