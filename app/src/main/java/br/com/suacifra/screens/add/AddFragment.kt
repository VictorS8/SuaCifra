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
import br.com.suacifra.database.DatabaseHelper
import br.com.suacifra.databinding.AddFragmentBinding
import br.com.suacifra.models.Cifras
import br.com.suacifra.screens.home.HomeFragment
import br.com.suacifra.screens.notes.NotesFragment
import br.com.suacifra.utils.getColorFromAttr
import br.com.suacifra.utils.mutableSetToString
import br.com.suacifra.utils.stringOfMutableListToEditTextString
import br.com.suacifra.utils.stringToMutableSet

class AddFragment : Fragment() {

    private lateinit var binding: AddFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var sequenceChordsList: MutableSet<String>? = mutableSetOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var sequenceAdapter: Adapter<SequenceChordsRecyclerViewAdapter.ViewHolder>
    private lateinit var sequenceLayoutManager: LayoutManager
    private var isEditCifraModeEnable = false
    private var isEditSequenceModeEnable = false
    private var cifraId: Int = 0
    private var cifraName: String? = ""
    private var cifraSingerName: String? = ""
    private var cifraTone: String? = ""
    private var cifraSequenceSetString: MutableSet<String>? = mutableSetOf()
    private var cifraSequenceString: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        val sharedPref = mainActivityContext.getSharedPreferences(
            getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE
        )

        binding.cifraNameEditText.setText(
            sharedPref.getString(
                getString(R.string.shared_preference_add_cifra_mode_name_edit_text_key),
                ""
            )
        )

        binding.cifraSingerNameEditText.setText(
            sharedPref.getString(
                getString(R.string.shared_preference_add_cifra_mode_singer_name_edit_text_key),
                ""
            )
        )

        binding.chooseToneButton.setOnClickListener {
            checkCardViewVisibility()
        }

        val toneString = sharedPref.getString(
            getString(R.string.shared_preference_edit_cifra_mode_tone_string_key),
            getString(R.string.tone_spinner_helper_string)
        )

        if (toneString == getString(R.string.tone_spinner_helper_string))
            binding.songToneButtonHelper.text = toneString
        else {
            binding.songToneButtonHelper.text = getString(
                R.string.tone_chosen_text, sharedPref.getString(
                    getString(R.string.shared_preference_edit_cifra_mode_tone_string_key),
                    getString(R.string.tone_spinner_helper_string)
                )
            )
        }

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
            cifraId = sharedPref.getInt(
                getString(R.string.shared_preference_edit_cifra_mode_id_int_key),
                cifraId
            )
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
            binding.cifraSingerNameEditText.setText(cifraSingerName ?: "")
            binding.songToneButtonHelper.text = getString(R.string.tone_chosen_text, cifraTone)
            binding.deleteCifraButton.visibility = View.VISIBLE
        } else {
            // if I clicked on add bottom navigation option
            cifraSequenceSetString = sharedPref.getStringSet(
                getString(R.string.shared_preference_edit_cifra_mode_sequence_set_string_key),
                cifraSequenceSetString
            )
            cifraTone = sharedPref.getString(
                getString(R.string.shared_preference_edit_cifra_mode_tone_string_key),
                cifraTone
            )
            cifraSequenceString = mutableSetToString(cifraSequenceSetString ?: mutableSetOf())
            sequenceChordsList = sharedPref.getStringSet(
                getString(R.string.shared_preference_edit_cifra_mode_sequence_set_string_key),
                mutableSetOf()
            )
            binding.deleteCifraButton.visibility = View.GONE
        }

        if (sequenceChordsList?.size == 0)
            binding.noSequenceMessage.visibility = View.VISIBLE
        else
            binding.noSequenceMessage.visibility = View.INVISIBLE


        recyclerView = binding.sequenceRecyclerView
        recyclerView.hasFixedSize()

        sequenceLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = sequenceLayoutManager

        sequenceAdapter = SequenceChordsRecyclerViewAdapter(
            sequenceChordsList ?: mutableSetOf(),
            mainActivityContext
        )
        recyclerView.adapter = sequenceAdapter

        binding.addSequencesImageButton.setOnClickListener {
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putString(
                getString(R.string.shared_preference_add_cifra_mode_name_edit_text_key),
                binding.cifraNameEditText.text.toString()
            )
            sharedPrefEditor.putString(
                getString(R.string.shared_preference_add_cifra_mode_singer_name_edit_text_key),
                binding.cifraSingerNameEditText.text.toString()
            )
            sharedPrefEditor.putBoolean(
                getString(R.string.shared_preference_edit_sequence_mode_boolean_key),
                false
            )
            sharedPrefEditor.apply()
            mainActivityContext.addToBackStackFragment(AddSequenceFragment())
        }

        val cifraNameEditText = binding.cifraNameEditText.text
        val cifraSingerNameEditText = binding.cifraSingerNameEditText.text
        val cifraSequenceSetString: MutableSet<String> = cifraSequenceSetString ?: mutableSetOf()
        cifraSequenceString = mutableSetToString(cifraSequenceSetString)

        binding.deleteCifraButton.setOnClickListener {
            if (isEditCifraModeEnable) {
                val databaseHelper = DatabaseHelper(mainActivityContext)
                val deleteStatus =
                    databaseHelper.deleteOneCifra(cifraId)
                if (deleteStatus > 0) {
                    mainActivityContext.toastMessage(
                        R.string.delete_cifra_successfully,
                        cifraName ?: "",
                        Toast.LENGTH_LONG
                    )
                    mainActivityContext.replaceFragment(HomeFragment())
                } else {
                    mainActivityContext.toastMessage(
                        R.string.delete_cifra_failed,
                        Toast.LENGTH_LONG
                    )
                }
            }
        }

        binding.saveCifraButton.setOnClickListener {
            if (!cifraNameEditText.isNullOrBlank() && !cifraTone.isNullOrBlank() && !cifraSingerNameEditText.isNullOrBlank() && cifraSequenceString.isNotBlank()) {
                val cifrasModel =
                    Cifras(
                        -1,
                        cifraNameEditText.toString().trim(),
                        cifraTone ?: "",
                        cifraSingerNameEditText.toString().trim(),
                        cifraSequenceString
                    )
                val databaseHelper = DatabaseHelper(mainActivityContext)
                if (isEditCifraModeEnable) {
                    cifrasModel.id = cifraId
                    val updateStatus = databaseHelper.updateOneCifra(cifrasModel)
                    if (updateStatus >= 0) {
                        mainActivityContext.toastMessage(
                            R.string.edit_cifra_successfully,
                            cifraNameEditText.toString().trim(),
                            Toast.LENGTH_LONG
                        )
                        mainActivityContext.replaceFragment(HomeFragment())
                    } else {
                        mainActivityContext.toastMessage(
                            R.string.save_cifra_missing_data,
                            Toast.LENGTH_LONG
                        )
                    }
                } else {
                    val insertStatus = databaseHelper.addOneCifra(cifrasModel)
                    if (insertStatus) {
                        mainActivityContext.toastMessage(
                            R.string.add_cifra_successfully,
                            cifraNameEditText.toString().trim(),
                            Toast.LENGTH_SHORT
                        )
                        mainActivityContext.replaceFragment(HomeFragment())
                    } else {
                        mainActivityContext.toastMessage(
                            R.string.save_cifra_missing_data,
                            Toast.LENGTH_LONG
                        )
                    }
                }
            } else if (cifraNameEditText.isNullOrBlank()) {
                binding.cifraNameEditText.setHintTextColor(
                    mainActivityContext.getColorFromAttr(
                        com.google.android.material.R.attr.errorTextColor
                    )
                )
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_data,
                    Toast.LENGTH_LONG
                )
            } else if (cifraSingerNameEditText.isNullOrBlank()) {
                binding.cifraNameEditText.setHintTextColor(
                    mainActivityContext.getColorFromAttr(
                        com.google.android.material.R.attr.errorTextColor
                    )
                )
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_data,
                    Toast.LENGTH_LONG
                )
            } else if (cifraTone.isNullOrBlank()) {
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_tone,
                    Toast.LENGTH_LONG
                )
            } else if (cifraSequenceString.isBlank()) {
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_sequence,
                    Toast.LENGTH_LONG
                )
            } else {
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_data,
                    Toast.LENGTH_LONG
                )
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
        val sharedPref = mainActivityContext.getSharedPreferences(
            getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE
        )
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putString(
            getString(R.string.shared_preference_edit_cifra_mode_tone_string_key),
            textViewString.text.toString()
        )
        sharedPrefEditor.apply()
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
