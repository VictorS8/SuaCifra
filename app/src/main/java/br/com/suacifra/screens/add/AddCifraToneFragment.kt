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
import br.com.suacifra.databinding.AddCifraToneFragmentBinding
import br.com.suacifra.utils.Config
import br.com.suacifra.utils.getColorFromAttr

class AddCifraToneFragment : Fragment() {

    private lateinit var binding: AddCifraToneFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var cifraTone: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_cifra_tone_fragment,
            container,
            false
        )

        mainActivityContext = (activity as MainActivity)

        val sharedPref = mainActivityContext.getSharedPreferences(
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )

        cifraTone = sharedPref.getString(
            Config.SHARED_PREFERENCE_CIFRA_TONE_STRING_KEY,
            cifraTone
        ) ?: ""

        binding.addCifraToneEditText.setText(
            cifraTone
        )

        binding.addCifraToneBackImageButton.setOnClickListener {
            val cifraEditText = binding.addCifraToneEditText.text
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putString(
                Config.SHARED_PREFERENCE_CIFRA_TONE_STRING_KEY,
                cifraEditText.toString()
            )
            sharedPrefEditor.apply()
            mainActivityContext.popBackStackFragment()
        }

        binding.addCifraToneNextImageButton.setOnClickListener {
            val cifraEditText = binding.addCifraToneEditText.text
            val toneRegex = "[A-GmM#bsu/()1-9]+".toRegex()
            if (!cifraEditText.isNullOrBlank() && toneRegex.matches(cifraEditText.toString())) {
                val sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.putString(
                    Config.SHARED_PREFERENCE_CIFRA_TONE_STRING_KEY,
                    cifraEditText.toString()
                )
                sharedPrefEditor.apply()
                mainActivityContext.addToBackStackFragment(AddCifraSequenceFragment())
            } else {
                binding.addCifraToneEditText.setHintTextColor(
                    mainActivityContext.getColorFromAttr(
                        com.google.android.material.R.attr.colorError
                    )
                )
                mainActivityContext.toastMessage(
                    R.string.save_cifra_tone_missing_data,
                    Toast.LENGTH_LONG
                )
            }
        }

        return binding.root
    }

}