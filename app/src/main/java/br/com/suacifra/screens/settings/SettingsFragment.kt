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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)

        binding.tunerButton.setOnClickListener {
            // TODO - Implement it
        }

        binding.loginButton.setOnClickListener {
            val oldAccount = (activity as MainActivity).getLastSignedInAccountOnActivity()
            if (oldAccount != null) {
                Toast.makeText(
                    (activity as MainActivity),
                    getString(R.string.sign_in_with_google_message_success),
                    Toast.LENGTH_SHORT
                )
                    .show()
                (activity as MainActivity).replaceFragmentOnSettings(ProfileFragment())
            } else {
                (activity as MainActivity).replaceFragmentOnSettings(LoginFragment())
            }
        }

        binding.aboutButton.setOnClickListener {
            (activity as MainActivity).replaceFragmentOnSettings(AboutFragment())
        }

        return binding.root
    }

//    private fun openLogin(nextActivity: Activity) {
//        val intent = Intent(activity, nextActivity::class.java)
//        activity?.startActivity(intent)
//    }

//    private fun View.hideKeyboard() {
//        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(windowToken, 0)
//    }

}
