package br.com.suacifra.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.SettingsFragmentBinding
import br.com.suacifra.screens.about.AboutFragment
import br.com.suacifra.screens.login.LoginFragment
import br.com.suacifra.screens.profile.ProfileFragment


class SettingsFragment : Fragment() {

    private lateinit var binding: SettingsFragmentBinding
    private lateinit var mainActivityContext: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        binding.tunerButton.setOnClickListener {
            // TODO - Implement it
        }

        binding.loginButton.setOnClickListener {
            val oldAccount = mainActivityContext.getLastSignedInAccountOnActivity()
            if (oldAccount != null) {
                Toast.makeText(
                    mainActivityContext,
                    getString(R.string.sign_in_with_google_message_success),
                    Toast.LENGTH_SHORT
                )
                    .show()
                mainActivityContext.replaceFragmentOnSettings(ProfileFragment())
            } else {
                mainActivityContext.replaceFragmentOnSettings(LoginFragment())
            }
        }

        binding.aboutButton.setOnClickListener {
            mainActivityContext.replaceFragmentOnSettings(AboutFragment())
        }

        return binding.root
    }

}
