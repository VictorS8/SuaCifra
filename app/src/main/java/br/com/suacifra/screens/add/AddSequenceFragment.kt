package br.com.suacifra.screens.add

import android.os.Bundle
import android.util.Log
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
import br.com.suacifra.utils.*

class AddSequenceFragment : Fragment() {

    private lateinit var binding: AddSequenceFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var cifraId: Int = 0
    private var cifraName: String? = ""
    private var cifraTone: String? = ""
    private var cifraSingerName: String? = ""
    private var cifraSequenceString: String? = ""
    private var cifraOneSequenceString: String? = ""
    private var cifraOneSequenceIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.add_sequence_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        val args = this.arguments

        if (args != null) {
            cifraId = args.getInt(Config.CIFRA_ID_BUNDLE_KEY)
            cifraSequenceString = args.getString(Config.CIFRA_SEQUENCE_STRING_BUNDLE_KEY)
            cifraOneSequenceString = args.getString(Config.CIFRA_ONE_SEQUENCE_BUNDLE_KEY)
            cifraOneSequenceIndex = args.getInt(Config.CIFRA_ONE_SEQUENCE_INDEX_BUNDLE_KEY)
            binding.sequenceEditText.setText(cifraOneSequenceString)
            binding.deleteSequenceButton.visibility = View.VISIBLE
        } else
            binding.deleteSequenceButton.visibility = View.GONE


        binding.deleteSequenceButton.setOnClickListener {
            if (args != null) {
                val cifraSequenceStringToMutableSet = stringToMutableSet(cifraSequenceString ?: "")
                cifraSequenceStringToMutableSet.remove(cifraOneSequenceString)
                cifraSequenceString = mutableSetToString(cifraSequenceStringToMutableSet)
                val databaseHelper = DatabaseHelper(mainActivityContext)
                databaseHelper.updateSequenceStringOfCifra(cifraId, cifraSequenceString ?: "")
                mainActivityContext.replaceFragment(AddFragment())
            }
        }



        binding.saveSequenceButton.setOnClickListener {
            if (!binding.sequenceEditText.text.isNullOrBlank()) {
                if (args != null) {
                    val sequenceEditText = binding.sequenceEditText.text.toString().trim()
                    val cifraSequenceStringToMutableSet = stringToMutableSet(cifraSequenceString ?: "")
                    cifraSequenceStringToMutableSet.addStringAt(
                        cifraOneSequenceIndex,
                        sequenceEditText
                    )
                    cifraSequenceString = mutableSetToString(cifraSequenceStringToMutableSet)
                    val databaseHelper = DatabaseHelper(mainActivityContext)
                    databaseHelper.updateSequenceStringOfCifra(cifraId, cifraSequenceString ?: "")
                    mainActivityContext.replaceFragment(AddFragment())
                    mainActivityContext.toastMessage(
                        R.string.edit_sequence_successfully,
                        Toast.LENGTH_LONG
                    )
                } else {
                    val sequenceEditText = binding.sequenceEditText.text.toString().trim()
                    Log.i("Cifra", "sequenceEditText $sequenceEditText")
                    val cifraSequenceStringToMutableSet = stringToMutableSet(cifraSequenceString ?: "")
                    Log.i("Cifra", "cifraSequenceStringToMutableSet $cifraSequenceStringToMutableSet")
                    val newMutableListOfString = cifraSequenceStringToMutableSet.toMutableList()
                    Log.i("Cifra", "newMutableListOfString $newMutableListOfString")
                    if (newMutableListOfString.size == 1) {
                        newMutableListOfString[0] = sequenceEditText
                        Log.i("Cifra", "if newMutableListOfString add $newMutableListOfString")
                    } else {
                        newMutableListOfString.add(sequenceEditText)
                        Log.i("Cifra", "else newMutableListOfString add $newMutableListOfString")
                    }
                    cifraSequenceString = mutableSetToString(newMutableListOfString.toMutableSet())
                    Log.i("Cifra", "after if newMutableListOfString add $newMutableListOfString")
                    Log.i("Cifra", "after if cifraSequenceString mutableSetToString $cifraSequenceString .toMutableSet ${cifraSequenceString is String}")
                    val bundle = Bundle()
                    bundle.putString(
                        Config.CIFRA_NAME_BUNDLE_KEY,
                        cifraName
                    )
                    bundle.putString(
                        Config.CIFRA_SINGER_NAME_BUNDLE_KEY,
                        cifraSingerName
                    )
                    bundle.putString(Config.CIFRA_TONE_BUNDLE_KEY, cifraTone)
                    bundle.putString(
                        Config.CIFRA_SEQUENCE_STRING_BUNDLE_KEY,
                        sequenceEditText
                    )
                    val fragment = AddFragment()
                    fragment.arguments = bundle
                    mainActivityContext.replaceFragment(fragment)
                    mainActivityContext.toastMessage(
                        R.string.add_sequence_successfully,
                        Toast.LENGTH_LONG
                    )
                }
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
