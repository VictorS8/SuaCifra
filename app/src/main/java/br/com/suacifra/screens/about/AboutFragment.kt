package br.com.suacifra.screens.about

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.BuildConfig
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.AboutFragmentBinding
import br.com.suacifra.screens.settings.SettingsFragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class AboutFragment : Fragment() {

    private lateinit var binding: AboutFragmentBinding
    private lateinit var mainActivityContext: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.about_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        binding.aboutThirdPartyTextView.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    OssLicensesMenuActivity::class.java
                )
            )
        }

        binding.aboutGoBackButton.setOnClickListener {
            mainActivityContext.addToBackStackFragment(SettingsFragment())
        }

        val stringAppVersion = "v${BuildConfig.VERSION_NAME}"

        binding.aboutVersionApp.text = stringAppVersion

        return binding.root
    }

}
