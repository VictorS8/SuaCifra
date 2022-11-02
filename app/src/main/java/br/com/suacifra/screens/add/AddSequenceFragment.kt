package br.com.suacifra.screens.add

import android.content.Context
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.database.SharedPreferencesSingleton
import br.com.suacifra.databinding.AddSequenceFragmentBinding
import br.com.suacifra.utils.Config
import br.com.suacifra.utils.addStringAt
import br.com.suacifra.utils.getColorFromAttr
import br.com.suacifra.utils.stringOfMutableListToEditTextString

class AddSequenceFragment : Fragment() {

    private lateinit var binding: AddSequenceFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var isEditSequenceModeEnable = false
    private var cifraId: Int = 0
    private var sequence: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.add_sequence_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        isEditSequenceModeEnable = SharedPreferencesSingleton.getData(
            mainActivityContext,
            Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_BOOLEAN_KEY,
            isEditSequenceModeEnable
        ) as Boolean

        if (isEditSequenceModeEnable) {
            // if I clicked in one custom cifra
            cifraId = SharedPreferencesSingleton.getData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_ID_INT_KEY,
                cifraId
            ) as Int
            sequence = SharedPreferencesSingleton.getData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_SEQUENCE_STRING_KEY,
                sequence
            ) as String
            binding.sequenceEditText.setText(stringOfMutableListToEditTextString(sequence))
            binding.deleteSequenceButton.visibility = View.VISIBLE
        } else {
            // if I clicked on add bottom navigation option
            cifraId = -1
            sequence = ""
            binding.deleteSequenceButton.visibility = View.GONE
        }

        binding.deleteSequenceButton.setOnClickListener {
            var deleteSequence: MutableSet<String> = mutableSetOf()
            deleteSequence = SharedPreferencesSingleton.getMutableSetData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                deleteSequence
            )

            val sequenceToDelete = sequence

            deleteSequence.remove(sequenceToDelete)

            SharedPreferencesSingleton.editor(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY, deleteSequence
            )
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
                    var mutableSetOfString: MutableSet<String> = mutableSetOf()
                    var editSequenceIndex = 0

                    mutableSetOfString = SharedPreferencesSingleton.getMutableSetData(
                        mainActivityContext,
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                        mutableSetOfString
                    )
                    editSequenceIndex = SharedPreferencesSingleton.getData(
                        mainActivityContext,
                        Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_SEQUENCE_INDEX_KEY,
                        editSequenceIndex
                    ) as Int

                    val newMutableSetOfString = mutableSetOfString.addStringAt(
                        editSequenceIndex,
                        sequenceEditText.toString().trim()
                    )
                    SharedPreferencesSingleton.mutableSetEditor(
                        mainActivityContext,
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                        newMutableSetOfString
                    )
                    Toast.makeText(
                        mainActivityContext,
                        getString(R.string.edit_sequence_successfully),
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    var mutableSetOfString: MutableSet<String> = mutableSetOf()
                    mutableSetOfString = SharedPreferencesSingleton.getMutableSetData(
                        mainActivityContext,
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                        mutableSetOfString
                    )

                    val newMutableListOfString = mutableSetOfString.toMutableList()
                    newMutableListOfString.add(sequenceEditText.toString().trim())
                    newMutableListOfString.toMutableSet()
                    val newMutableSetOfString = newMutableListOfString.toMutableSet()
                    SharedPreferencesSingleton.mutableSetEditor(
                        mainActivityContext,
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                        newMutableSetOfString
                    )
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
