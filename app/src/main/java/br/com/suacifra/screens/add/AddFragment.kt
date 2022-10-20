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
import br.com.suacifra.database.DatabaseHelper
import br.com.suacifra.databinding.AddFragmentBinding
import br.com.suacifra.models.Cifras
import br.com.suacifra.screens.home.HomeFragment
import br.com.suacifra.screens.notes.NotesFragment
import br.com.suacifra.utils.Config
import br.com.suacifra.utils.getColorFromAttr
import br.com.suacifra.utils.stringToMutableSet

class AddFragment : Fragment() {

    private lateinit var binding: AddFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var sequenceChordsList: MutableSet<String>? = mutableSetOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var sequenceAdapter: Adapter<SequenceChordsRecyclerViewAdapter.ViewHolder>
    private lateinit var sequenceLayoutManager: LayoutManager
    private var cifraId: Int = 0
    private var cifraName: String? = ""
    private var cifraSingerName: String? = ""
    private var cifraTone: String? = ""
    private var cifraChordsSequenceString: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        binding.chooseToneButton.setOnClickListener {
            checkCardViewVisibility()
        }

        val args = this.arguments

        if (args != null) {
            cifraId = args.getInt(Config.CIFRA_ID_BUNDLE_KEY)
            val databaseHelper = DatabaseHelper(mainActivityContext)
            val chosenCifra = databaseHelper.getOneCifra(cifraId)
            if (chosenCifra != null) {
                cifraName = chosenCifra.name
                cifraTone = chosenCifra.tone
                cifraSingerName = chosenCifra.singerName
                cifraChordsSequenceString = chosenCifra.chordsSequence
                binding.cifraNameEditText.setText(cifraName)
                if (cifraTone == "")
                    binding.songToneButtonHelper.text =
                        getString(R.string.tone_spinner_helper_string)
                else
                    binding.songToneButtonHelper.text =
                        getString(R.string.tone_chosen_text, cifraTone)
                binding.cifraSingerNameEditText.setText(cifraSingerName)
                sequenceChordsList = stringToMutableSet(cifraChordsSequenceString ?: "")
                binding.deleteCifraButton.visibility = View.VISIBLE
            } else {
                cifraName = args.getString(Config.CIFRA_NAME_BUNDLE_KEY)
                cifraTone = args.getString(Config.CIFRA_TONE_BUNDLE_KEY)
                cifraSingerName = args.getString(Config.CIFRA_SINGER_NAME_BUNDLE_KEY)
                cifraChordsSequenceString = args.getString(Config.CIFRA_SEQUENCE_STRING_BUNDLE_KEY)
                binding.cifraNameEditText.setText(cifraName)
                if (cifraTone == "")
                    binding.songToneButtonHelper.text =
                        getString(R.string.tone_spinner_helper_string)
                else
                    binding.songToneButtonHelper.text =
                        getString(R.string.tone_chosen_text, cifraTone)
                binding.cifraSingerNameEditText.setText(cifraSingerName)
                sequenceChordsList = stringToMutableSet(cifraChordsSequenceString ?: "")
            }
        }

        if (sequenceChordsList?.size == 0)
            binding.noSequenceMessage.visibility = View.VISIBLE
        else
            binding.noSequenceMessage.visibility = View.GONE

        recyclerView = binding.sequenceRecyclerView
        recyclerView.hasFixedSize()

        sequenceLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = sequenceLayoutManager

        sequenceAdapter = SequenceChordsRecyclerViewAdapter(
            cifraChordsSequenceString ?: "",
            mainActivityContext
        )
        recyclerView.adapter = sequenceAdapter

        binding.addSequencesImageButton.setOnClickListener {
            mainActivityContext.addToBackStackFragment(AddSequenceFragment())
        }

        binding.deleteCifraButton.setOnClickListener {
            if (args != null) {
                val databaseHelper = DatabaseHelper(mainActivityContext)
                val deleteStatus =
                    databaseHelper.deleteOneCifra(args.getInt(Config.CIFRA_ID_BUNDLE_KEY))
                if (deleteStatus > 0) {
                    mainActivityContext.toastMessage(
                        R.string.delete_cifra_successfully,
                        cifraName ?: "",
                        Toast.LENGTH_LONG
                    )
                } else {
                    mainActivityContext.toastMessage(
                        R.string.delete_cifra_failed,
                        Toast.LENGTH_LONG
                    )
                }
            }
        }

        val cifraNameEditText = binding.cifraNameEditText.text
        val cifraSingerNameEditText = binding.cifraSingerNameEditText.text

        binding.saveCifraButton.setOnClickListener {
            if (!cifraNameEditText.isNullOrBlank() && !cifraTone.isNullOrBlank() && !cifraSingerNameEditText.isNullOrBlank() && cifraChordsSequenceString.isNullOrBlank()) {
                val cifrasModel =
                    Cifras(
                        -1,
                        cifraNameEditText.toString().trim(),
                        cifraTone ?: "",
                        cifraSingerNameEditText.toString().trim(),
                        cifraChordsSequenceString ?: ""
                    )
                val databaseHelper = DatabaseHelper(mainActivityContext)
                if (args != null) {
                    val updateStatus = databaseHelper.updateOneCifra(cifrasModel)
                    if (updateStatus >= 0) {
                        mainActivityContext.toastMessage(
                            R.string.edit_cifra_successfully,
                            cifraNameEditText.toString().trim(),
                            Toast.LENGTH_LONG
                        )
                        mainActivityContext.replaceFragment(NotesFragment())
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
            } else if (cifraChordsSequenceString.isNullOrBlank()) {
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

    private fun clickableTextAsTextViewEvent(view: View): String {
        val textViewString: TextView = view as TextView
        cifraTone = textViewString.text.toString()
        val returnToneText = getString(R.string.tone_chosen_text, textViewString.text)

        Toast.makeText(context, returnToneText, Toast.LENGTH_LONG).show()
        binding.songToneButtonHelper.text = returnToneText
        checkCardViewVisibility()
        return textViewString.text.toString()
    }

    private fun allToneClickEvent() {
        binding.apply {
            aToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            aSharpToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            bToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            cToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            cSharpToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            dToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            dSharpToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            eToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            fToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            fSharpToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            gToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
            gSharpToneTextView.setOnClickListener { cifraTone = clickableTextAsTextViewEvent(it) }
        }
    }

}

