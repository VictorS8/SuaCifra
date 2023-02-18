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
import br.com.suacifra.databinding.AddCifraSingerNameFragmentBinding
import br.com.suacifra.utils.Config
import br.com.suacifra.utils.getColorFromAttr

class AddCifraSingerNameFragment : Fragment() {

    private lateinit var binding: AddCifraSingerNameFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private var cifraSingerName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_cifra_singer_name_fragment,
            container,
            false
        )

        mainActivityContext = (activity as MainActivity)

        val sharedPref = mainActivityContext.getSharedPreferences(
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )

        cifraSingerName = sharedPref.getString(
            Config.SHARED_PREFERENCE_CIFRA_SINGER_NAME_STRING_KEY,
            cifraSingerName
        ) ?: ""

        binding.cifraSingerNameEditText.setText(
            cifraSingerName
        )

        binding.addCifraSingerNameBackImageButton.setOnClickListener {
            val cifraNameEditText = binding.cifraSingerNameEditText.text
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putString(
                Config.SHARED_PREFERENCE_CIFRA_SINGER_NAME_STRING_KEY,
                cifraNameEditText.toString()
            )
            sharedPrefEditor.apply()
            mainActivityContext.popBackStackFragment()
        }

        binding.addCifraSingerNameNextImageButton.setOnClickListener {
            val cifraNameEditText = binding.cifraSingerNameEditText.text
            if (!cifraNameEditText.isNullOrBlank()) {
                val sharedPrefEditor = sharedPref.edit()
                sharedPrefEditor.putString(
                    Config.SHARED_PREFERENCE_CIFRA_SINGER_NAME_STRING_KEY,
                    cifraNameEditText.toString()
                )
                sharedPrefEditor.apply()
                mainActivityContext.addToBackStackFragment(AddCifraToneFragment())
            } else if (cifraNameEditText.isNullOrBlank()) {
                binding.cifraSingerNameEditText.setHintTextColor(
                    mainActivityContext.getColorFromAttr(
                        com.google.android.material.R.attr.colorError
                    )
                )
                mainActivityContext.toastMessage(
                    R.string.save_cifra_singer_name_missing_data,
                    Toast.LENGTH_LONG
                )
            }
        }

        return binding.root
    }

}
