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
import br.com.suacifra.utils.stringOfMutableListToEditTextString

class AddSequenceFragment : Fragment() {

    private lateinit var binding: AddSequenceFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var isEditSequenceModeEnable = false
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
            sequence = sharedPref.getString(
                getString(R.string.shared_preference_edit_sequence_mode_sequence_string_key),
                sequence
            )
            binding.sequenceEditText.setText(stringOfMutableListToEditTextString(sequence ?: ""))
        } else {
            // if I clicked on add bottom navigation option
            sequence = ""
        }

        binding.cancelSequenceImageButton.setOnClickListener {
            Toast.makeText(
                mainActivityContext,
                getString(R.string.add_note_cancelled),
                Toast.LENGTH_LONG
            )
                .show()
            mainActivityContext.replaceFragment(AddFragment())
        }

        binding.addSequenceImageButton.setOnClickListener {
            Toast.makeText(
                mainActivityContext,
                getString(R.string.add_sequence_successfully),
                Toast.LENGTH_LONG
            )
                .show()
            mainActivityContext.replaceFragment(AddFragment())
        }

        return binding.root
    }

}
