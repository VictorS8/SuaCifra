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
import br.com.suacifra.databinding.AddCifraNameFragmentBinding
import br.com.suacifra.utils.Config
import br.com.suacifra.utils.getColorFromAttr

class AddCifraNameFragment : Fragment() {

    private lateinit var binding: AddCifraNameFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var cifraName: String = ""
    private var isEditCifraNameModeEnable = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_cifra_name_fragment,
            container,
            false
        )

        mainActivityContext = (activity as MainActivity)

        val sharedPref = mainActivityContext.getSharedPreferences(
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )

        isEditCifraNameModeEnable = sharedPref.getBoolean(
            Config.SHARED_PREFERENCE_EDIT_CIFRA_NAME_MODE_BOOLEAN_KEY,
            isEditCifraNameModeEnable
        )

        cifraName = sharedPref.getString(
            Config.SHARED_PREFERENCE_CIFRA_NAME_STRING_KEY,
            cifraName
        ) ?: ""

        binding.cifraNameEditText.setText(
            cifraName
        )

        binding.addCifraNameNextImageButton.setOnClickListener {
            val cifraNameEditText = binding.cifraNameEditText.text
            if (!cifraNameEditText.isNullOrBlank()) {
                val sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.putString(
                    Config.SHARED_PREFERENCE_CIFRA_NAME_STRING_KEY,
                    cifraNameEditText.toString()
                )
                sharedPrefEditor.apply()
                if (isEditCifraNameModeEnable)
                    mainActivityContext.popBackStackFragment()
                else
                    mainActivityContext.addToBackStackFragment(AddCifraSingerNameFragment())
            } else {
                binding.cifraNameEditText.setHintTextColor(
                    mainActivityContext.getColorFromAttr(
                        com.google.android.material.R.attr.colorError
                    )
                )
                mainActivityContext.toastMessage(
                    R.string.save_cifra_name_missing_data,
                    Toast.LENGTH_LONG
                )
            }
        }

        return binding.root
    }

}
