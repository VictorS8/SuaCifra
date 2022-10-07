package br.com.suacifra.screens.profile

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
import br.com.suacifra.databinding.ProfileFragmentBinding
import br.com.suacifra.screens.login.LoginFragment
import br.com.suacifra.screens.settings.SettingsFragment
import coil.api.load
import coil.transform.RoundedCornersTransformation


class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private lateinit var mainActivityContext: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        val sharedPref = mainActivityContext.getSharedPreferences(
            getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE
        )

        val googleAccount = mainActivityContext.getLastSignedInAccountOnActivity()

        if (googleAccount != null) {
            binding.profilePictureImageView.load(
                googleAccount.photoUrl.toString().replace("s96-c", "s768-c")
            ) {
                transformations(
                    RoundedCornersTransformation(
                        topLeft = 12.0f,
                        topRight = 12.0f,
                        bottomLeft = 12.0f,
                        bottomRight = 12.0f
                    )
                )
            }
            binding.profileEmailTextView.text = googleAccount.email
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putBoolean(
                getString(R.string.shared_preference_sign_in_boolean_key),
                true
            )
            sharedPrefEditor.apply()
        }

        binding.profileSignOutButton.setOnClickListener {
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putBoolean(
                getString(R.string.shared_preference_sign_in_boolean_key),
                false
            )
            sharedPrefEditor.apply()

            mainActivityContext.signOutOnActivity()
            Toast.makeText(
                mainActivityContext,
                getString(R.string.sign_out_with_google),
                Toast.LENGTH_LONG
            ).show()
            mainActivityContext.replaceFragment(LoginFragment())

        }

        binding.profileGoBackButton.setOnClickListener {
            mainActivityContext.replaceFragment(SettingsFragment())
        }

        return binding.root
    }

}
