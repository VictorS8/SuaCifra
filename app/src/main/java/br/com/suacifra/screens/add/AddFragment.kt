package br.com.suacifra.screens.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.AddFragmentBinding
import br.com.suacifra.utils.mutableSetToMutableListOfString
import br.com.suacifra.utils.stringOfMutableListToEditTextString

class AddFragment : Fragment() {

    private lateinit var binding: AddFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private lateinit var sequenceChordsList: MutableList<MutableList<String>>
    private lateinit var recyclerView: RecyclerView
    private lateinit var sequenceAdapter: Adapter<SequenceChordsRecyclerViewAdapter.ViewHolder>
    private lateinit var sequenceLayoutManager: LayoutManager
    private var isEditCifraModeEnable = false
    private var isEditSequenceModeEnable = false
    private var cifraName: String? = ""
    private var cifraSingerName: String? = ""
    private var cifraTone: String? = ""
    private var cifraSequenceSetString: MutableSet<String>? = mutableSetOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        binding.chooseToneButton.setOnClickListener {
            checkCardViewVisibility()
        }

        val sharedPref = mainActivityContext.getSharedPreferences(
            getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE
        )

        isEditCifraModeEnable = sharedPref.getBoolean(
            getString(R.string.shared_preference_edit_cifra_mode_boolean_key),
            isEditCifraModeEnable
        )

        isEditSequenceModeEnable = sharedPref.getBoolean(
            getString(R.string.shared_preference_edit_sequence_mode_boolean_key),
            isEditSequenceModeEnable
        )

        if (isEditCifraModeEnable) {
            // if I clicked in one custom cifra
            cifraName = sharedPref.getString(
                getString(R.string.shared_preference_edit_cifra_mode_name_string_key),
                cifraName
            )
            cifraSingerName = sharedPref.getString(
                getString(R.string.shared_preference_edit_cifra_mode_singer_name_string_key),
                cifraSingerName
            )
            cifraTone = sharedPref.getString(
                getString(R.string.shared_preference_edit_cifra_mode_tone_string_key),
                cifraTone
            )
            cifraSequenceSetString = sharedPref.getStringSet(
                getString(R.string.shared_preference_edit_cifra_mode_sequence_set_string_key),
                cifraSequenceSetString
            )

            binding.cifraNameEditText.setText(stringOfMutableListToEditTextString(cifraName ?: ""))
            binding.cifraSingerNameEditText.setText(
                stringOfMutableListToEditTextString(
                    cifraSingerName ?: ""
                )
            )
            binding.songToneButtonHelper.text = getString(R.string.tone_chosen_text, cifraTone)
            sequenceChordsList =
                mutableSetToMutableListOfString(cifraSequenceSetString ?: mutableSetOf())
        } else {
            // if I clicked on add bottom navigation option
            sequenceChordsList = mutableListOf()
        }

        if (sequenceChordsList.size == 0)
            binding.noSequenceMessage.visibility = View.VISIBLE
        else
            binding.noSequenceMessage.visibility = View.INVISIBLE


        recyclerView = binding.sequenceRecyclerView
        recyclerView.hasFixedSize()

        sequenceLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = sequenceLayoutManager

        sequenceAdapter = SequenceChordsRecyclerViewAdapter(sequenceChordsList, mainActivityContext)
        recyclerView.adapter = sequenceAdapter

        binding.addSequencesImageButton.setOnClickListener {
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putBoolean(
                getString(R.string.shared_preference_edit_sequence_mode_boolean_key),
                false
            )
            sharedPrefEditor.apply()
            mainActivityContext.addToBackStackFragment(AddSequenceFragment())
        }

        binding.saveCifraButton.setOnClickListener {
            if (isEditCifraModeEnable) {
                // TODO - Edit Mode - Database handle
                Toast.makeText(
                    mainActivityContext,
                    getString(R.string.edit_cifra_successfully, binding.cifraNameEditText.text),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // TODO - Create Mode - Database handle
                Toast.makeText(
                    mainActivityContext,
                    getString(R.string.add_cifra_successfully, binding.cifraNameEditText.text),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        allToneClickEvent()

        return binding.root
    }

    private fun checkCardViewVisibility() {
        binding.apply {
            chooseToneCardView.visibility =
                if (chooseToneCardView.isVisible) View.GONE else View.VISIBLE
        }
    }

    private fun clickableTextAsTextViewEvent(view: View) {
        val textViewString: TextView = view as TextView
        val returnToneText = getString(R.string.tone_chosen_text, textViewString.text)

        Toast.makeText(context, returnToneText, Toast.LENGTH_LONG).show()
        binding.songToneButtonHelper.text = returnToneText
        checkCardViewVisibility()
    }

    private fun allToneClickEvent() {

        binding.apply {
            aToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            bbToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            bToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            cToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            dbToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            dToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            ebToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            eToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            fToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            gbToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            gToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            abToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
        }
    }

}
