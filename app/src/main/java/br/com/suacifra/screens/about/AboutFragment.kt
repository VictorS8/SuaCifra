package br.com.suacifra.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.BuildConfig
import br.com.suacifra.R
import br.com.suacifra.databinding.AboutFragmentBinding
import br.com.suacifra.databinding.MainActivityBinding
import br.com.suacifra.screens.settings.SettingsFragment

class AboutFragment : Fragment() {

    private lateinit var binding: AboutFragmentBinding
    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.about_fragment, container, false)
        mainActivityBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_activity, container, false)

        val appVersion = BuildConfig.VERSION_NAME
        val stringAppVersion = "v$appVersion"

        binding.aboutVersionApp.text = stringAppVersion

        binding.aboutGoBackButton.setOnClickListener {
            goBackToSettings()
        }

        return binding.root
    }

    private fun goBackToSettings() {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out
        )
        fragmentTransaction.replace(mainActivityBinding.mainFrameLayout.id, SettingsFragment())
        fragmentTransaction.commit()
    }

}
