package br.com.suacifra.screens.add

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
import br.com.suacifra.database.RoomDatabaseBuilderSingleton
import br.com.suacifra.database.SharedPreferencesSingleton
import br.com.suacifra.databinding.AddFragmentBinding
import br.com.suacifra.models.Cifras
import br.com.suacifra.utils.Config
import br.com.suacifra.utils.dataMutableSetToString
import br.com.suacifra.utils.getColorFromAttr
import br.com.suacifra.utils.stringOfMutableListToEditTextString

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        val roomDatabase = RoomDatabaseBuilderSingleton.databaseBuild(mainActivityContext)

        binding.cifraNameEditText.setText(
            SharedPreferencesSingleton.getData(
                mainActivityContext, Config.SHARED_PREFERENCE_ADD_CIFRA_MODE_NAME_EDIT_TEXT_KEY, ""
            ) as String
        )

        binding.cifraSingerNameEditText.setText(
            SharedPreferencesSingleton.getData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_ADD_CIFRA_MODE_SINGER_NAME_EDIT_TEXT_KEY,
                ""
            ) as String
        )

        binding.chooseToneButton.setOnClickListener {
            checkCardViewVisibility()
        }

        val toneString = SharedPreferencesSingleton.getData(
            mainActivityContext,
            Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
            getString(R.string.tone_spinner_helper_string)
        )

        if (toneString == getString(R.string.tone_spinner_helper_string)) binding.songToneButtonHelper.text =
            getString(R.string.tone_spinner_helper_string)
        else {
            binding.songToneButtonHelper.text = getString(
                R.string.tone_chosen_text, SharedPreferencesSingleton.getData(
                    mainActivityContext,
                    Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
                    getString(R.string.tone_spinner_helper_string)
                )
            )
        }

        isEditCifraModeEnable = SharedPreferencesSingleton.getData(
            mainActivityContext,
            Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_BOOLEAN_KEY,
            isEditCifraModeEnable
        ) as Boolean

        isEditSequenceModeEnable = SharedPreferencesSingleton.getData(
            mainActivityContext,
            Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_BOOLEAN_KEY,
            isEditSequenceModeEnable
        ) as Boolean

        if (isEditCifraModeEnable) {
            // if I clicked in one custom cifra
            cifraId = SharedPreferencesSingleton.getData(
                mainActivityContext, Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_ID_INT_KEY,
                cifraId
            ) as Int
            cifraName = SharedPreferencesSingleton.getData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_NAME_STRING_KEY,
                cifraName
            ) as String
            cifraSingerName = SharedPreferencesSingleton.getData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SINGER_NAME_STRING_KEY,
                cifraSingerName
            ) as String
            cifraTone = SharedPreferencesSingleton.getData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
                cifraTone
            ) as String
            cifraSequenceSetString = SharedPreferencesSingleton.getMutableSetData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                cifraSequenceSetString
            )

            binding.cifraNameEditText.setText(stringOfMutableListToEditTextString(cifraName))
            binding.cifraSingerNameEditText.setText(cifraSingerName)
            binding.songToneButtonHelper.text = getString(R.string.tone_chosen_text, cifraTone)
            binding.deleteCifraButton.visibility = View.VISIBLE
        } else {
            // if I clicked on add bottom navigation option
            cifraSequenceSetString = SharedPreferencesSingleton.getMutableSetData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                cifraSequenceSetString
            )
            cifraTone = SharedPreferencesSingleton.getData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
                cifraTone
            ) as String
            cifraSequenceString = cifraSequenceSetString.dataMutableSetToString()
            binding.deleteCifraButton.visibility = View.GONE
        }

        if (cifraSequenceSetString.size == 0) binding.noSequenceMessage.visibility = View.VISIBLE
        else binding.noSequenceMessage.visibility = View.INVISIBLE


        recyclerView = binding.sequenceRecyclerView
        recyclerView.hasFixedSize()

        sequenceLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = sequenceLayoutManager

        sequenceAdapter = SequenceChordsRecyclerViewAdapter(
            cifraSequenceSetString, mainActivityContext
        )
        recyclerView.adapter = sequenceAdapter

        binding.addSequencesImageButton.setOnClickListener {
            SharedPreferencesSingleton.editor(
                mainActivityContext,
                Config.SHARED_PREFERENCE_ADD_CIFRA_MODE_NAME_EDIT_TEXT_KEY,
                binding.cifraNameEditText.text.toString()
            )
            SharedPreferencesSingleton.editor(
                mainActivityContext,
                Config.SHARED_PREFERENCE_ADD_CIFRA_MODE_SINGER_NAME_EDIT_TEXT_KEY,
                binding.cifraSingerNameEditText.text.toString()
            )
            SharedPreferencesSingleton.editor(
                mainActivityContext, Config.SHARED_PREFERENCE_EDIT_SEQUENCE_MODE_BOOLEAN_KEY, false
            )
            mainActivityContext.addToBackStackFragment(AddSequenceFragment())
        }

        val cifraNameEditText = binding.cifraNameEditText.text
        val cifraSingerNameEditText = binding.cifraSingerNameEditText.text
        val cifraSequenceSetString: MutableSet<String> = cifraSequenceSetString
        cifraSequenceString = cifraSequenceSetString.dataMutableSetToString()

        binding.deleteCifraButton.setOnClickListener {
            if (isEditCifraModeEnable) {
                roomDatabase.cifrasDao().deleteOneCifraById(cifraId)
//                val databaseHelper = DatabaseHelper(mainActivityContext)
//                val deleteStatus =
//                    databaseHelper.deleteOneCifra(cifraId)
//                if (deleteStatus > 0) {
//                    mainActivityContext.toastMessage(
//                        R.string.delete_cifra_successfully,
//                        cifraName ?: "",
//                        Toast.LENGTH_LONG
//                    )
//                    mainActivityContext.replaceFragment(HomeFragment())
//                } else {
//                    mainActivityContext.toastMessage(
//                        R.string.delete_cifra_failed,
//                        Toast.LENGTH_LONG
//                    )
//                }
            }
        }

        binding.saveCifraButton.setOnClickListener {
            cifraTone = SharedPreferencesSingleton.getData(
                mainActivityContext,
                Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
                cifraTone
            ) as String
            if (!cifraNameEditText.isNullOrBlank() && cifraTone.isNotBlank() && !cifraSingerNameEditText.isNullOrBlank()) {
//                val databaseHelper = DatabaseHelper(mainActivityContext)
                if (isEditCifraModeEnable) {
                    cifraId = SharedPreferencesSingleton.getData(
                        mainActivityContext,
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_ID_INT_KEY,
                        -1
                    ) as Int
                    roomDatabase.cifrasDao().updateOneCifra(
                        Cifras(
                            cifraId,
                            cifraNameEditText.toString().trim(),
                            cifraTone,
                            cifraSingerNameEditText.toString().trim(),
                            cifraSequenceString
                        )
                    )
//                    val updateStatus = databaseHelper.updateOneCifra(cifrasModel)
//                    if (updateStatus >= 0) {
//                        mainActivityContext.toastMessage(
//                            R.string.edit_cifra_successfully,
//                            cifraNameEditText.toString().trim(),
//                            Toast.LENGTH_LONG
//                        )
//                        mainActivityContext.replaceFragment(HomeFragment())
//                    } else {
//                        mainActivityContext.toastMessage(
//                            R.string.save_cifra_missing_data,
//                            Toast.LENGTH_LONG
//                        )
//                    }
                } else {
                    roomDatabase.cifrasDao().createOneCifra(
                        Cifras(
                            -1,
                            cifraNameEditText.toString().trim(),
                            cifraTone,
                            cifraSingerNameEditText.toString().trim(),
                            cifraSequenceString
                        )
                    )
//                    val insertStatus = databaseHelper.addOneCifra(cifrasModel)
//                    if (insertStatus) {
//                        mainActivityContext.toastMessage(
//                            R.string.add_cifra_successfully,
//                            cifraNameEditText.toString().trim(),
//                            Toast.LENGTH_SHORT
//                        )
//                        mainActivityContext.replaceFragment(HomeFragment())
//                    } else {
//                        mainActivityContext.toastMessage(
//                            R.string.save_cifra_missing_data,
//                            Toast.LENGTH_LONG
//                        )
//                    }
                }
            } else if (cifraNameEditText.isNullOrBlank()) {
                binding.cifraNameEditText.setHintTextColor(
                    mainActivityContext.getColorFromAttr(
                        com.google.android.material.R.attr.errorTextColor
                    )
                )
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_data, Toast.LENGTH_LONG
                )
            } else if (cifraSingerNameEditText.isNullOrBlank()) {
                binding.cifraNameEditText.setHintTextColor(
                    mainActivityContext.getColorFromAttr(
                        com.google.android.material.R.attr.errorTextColor
                    )
                )
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_data, Toast.LENGTH_LONG
                )
            } else if (cifraTone.isBlank()) {
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_tone, Toast.LENGTH_LONG
                )
            } else if (cifraSequenceString.isBlank()) {
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_sequence, Toast.LENGTH_LONG
                )
            } else {
                mainActivityContext.toastMessage(
                    R.string.save_cifra_missing_data, Toast.LENGTH_LONG
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
        SharedPreferencesSingleton.editor(
            mainActivityContext,
            Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
            textViewString.text.toString()
        )
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
