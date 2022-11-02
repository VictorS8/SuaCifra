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
import br.com.suacifra.utils.*

class AddFragment : Fragment() {

    private lateinit var binding: AddFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private lateinit var recyclerView: RecyclerView
    private lateinit var sequenceAdapter: Adapter<SequenceChordsRecyclerViewAdapter.ViewHolder>
    private lateinit var sequenceLayoutManager: LayoutManager
    private var isEditCifraModeEnable = false
    private var isEditSequenceModeEnable = false
    private var cifraId: Int = 0
    private var cifraName: String = ""
    private var cifraSingerName: String = ""
    private var cifraTone: String = ""
    private var cifraSequenceSetString: MutableSet<String> = mutableSetOf()
    private var cifraSequenceString: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        val sharedPref = mainActivityContext.getSharedPreferences(
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )

        binding.cifraNameEditText.setText(
            sharedPref.getString(
                Config.SHARED_PREFERENCE_ADD_CIFRA_MODE_NAME_EDIT_TEXT_KEY,
                ""
            )
        )

        binding.cifraSingerNameEditText.setText(
            sharedPref.getString(
                Config.SHARED_PREFERENCE_ADD_CIFRA_MODE_SINGER_NAME_EDIT_TEXT_KEY,
                ""
            )
        )

        binding.chooseToneButton.setOnClickListener {
            checkCardViewVisibility()
        }

        val toneString = sharedPref.getString(
            Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
            getString(R.string.tone_spinner_helper_string)
        )

        if (toneString == getString(R.string.tone_spinner_helper_string))
            binding.songToneButtonHelper.text = toneString
        else {
            binding.songToneButtonHelper.text = getString(
                R.string.tone_chosen_text, sharedPref.getString(
                    Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
                    getString(R.string.tone_spinner_helper_string)
                )
            )
        }

        isEditCifraModeEnable = sharedPref.getBoolean(
            Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_BOOLEAN_KEY,
            isEditCifraModeEnable
        )

        isEditSequenceModeEnable = sharedPref.getBoolean(
            Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_BOOLEAN_KEY,
            isEditSequenceModeEnable
        )

        if (isEditCifraModeEnable) {
            // if I clicked in one custom cifra
            cifraId = sharedPref.getInt(
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_ID_INT_KEY,
                cifraId
            )
            cifraName = sharedPref.getString(
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_NAME_STRING_KEY,
                cifraName
            ) ?: ""
            cifraSingerName = sharedPref.getString(
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SINGER_NAME_STRING_KEY,
                cifraSingerName
            ) ?: ""
            cifraTone = sharedPref.getString(
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
                cifraTone
            ) ?: ""
            cifraSequenceSetString = sharedPref.getStringSet(
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                cifraSequenceSetString
            ) ?: mutableSetOf()

            binding.cifraNameEditText.setText(stringOfMutableListToEditTextString(cifraName))
            binding.cifraSingerNameEditText.setText(cifraSingerName)
            binding.songToneButtonHelper.text = getString(R.string.tone_chosen_text, cifraTone)
            binding.deleteCifraButton.visibility = View.VISIBLE
        } else {
            // if I clicked on add bottom navigation option
            cifraSequenceSetString = sharedPref.getStringSet(
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                cifraSequenceSetString
            ) ?: mutableSetOf()
            cifraTone = sharedPref.getString(
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
                cifraTone
            ) ?: ""
            cifraSequenceString = cifraSequenceSetString.dataMutableSetToString()
            binding.deleteCifraButton.visibility = View.GONE
        }

        if (cifraSequenceSetString.size == 0)
            binding.noSequenceMessage.visibility = View.VISIBLE
        else
            binding.noSequenceMessage.visibility = View.INVISIBLE

        recyclerView = binding.sequenceRecyclerView
        recyclerView.hasFixedSize()

        sequenceLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = sequenceLayoutManager

        sequenceAdapter = SequenceChordsRecyclerViewAdapter(
            cifraSequenceSetString,
            mainActivityContext
        )
        recyclerView.adapter = sequenceAdapter

        binding.addSequencesImageButton.setOnClickListener {
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putString(
                Config.SHARED_PREFERENCE_ADD_CIFRA_MODE_NAME_EDIT_TEXT_KEY,
                binding.cifraNameEditText.text.toString()
            )
            sharedPrefEditor.putString(
                Config.SHARED_PREFERENCE_ADD_CIFRA_MODE_SINGER_NAME_EDIT_TEXT_KEY,
                binding.cifraSingerNameEditText.text.toString()
            )
            sharedPrefEditor.putBoolean(
                Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_BOOLEAN_KEY,
                false
            )
            sharedPrefEditor.apply()
            mainActivityContext.addToBackStackFragment(AddSequenceFragment())
        }

        val cifraNameEditText = binding.cifraNameEditText.text
        val cifraSingerNameEditText = binding.cifraSingerNameEditText.text
        val cifraSequenceSetString: MutableSet<String> = cifraSequenceSetString
        cifraSequenceString = cifraSequenceSetString.dataMutableSetToString()

        binding.deleteCifraButton.setOnClickListener {
            if (isEditCifraModeEnable) {
                val databaseHelper = DatabaseHelper(mainActivityContext)
                val deleteStatus =
                    databaseHelper.deleteOneCifra(cifraId)
                if (deleteStatus > 0) {
                    mainActivityContext.toastMessage(
                        R.string.delete_cifra_successfully,
                        cifraName,
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
            cifraTone =
                sharedPref.getString(Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY, "")
                    ?: ""
            if (!cifraNameEditText.isNullOrBlank() && cifraTone.isNotBlank() && !cifraSingerNameEditText.isNullOrBlank()) {
                val cifrasModel =
                    Cifras(
                        -1,
                        cifraNameEditText.toString().trim(),
                        cifraTone,
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
            } else if (cifraTone.isBlank()) {
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_tone,
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
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )
        val sharedPrefEditor = sharedPref.edit()
        sharedPrefEditor.putString(
            Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
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
            aSharpToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            bToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            cToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            cSharpToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            dToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            dSharpToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            eToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            fToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            fSharpToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            gToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            gSharpToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
        }
    }

}
