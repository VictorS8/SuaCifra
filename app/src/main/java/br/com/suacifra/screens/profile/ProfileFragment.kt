package br.com.suacifra.screens.profile

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

        val googleAccount = mainActivityContext.getLastSignedInAccountOnActivity()

        if (googleAccount != null) {
            binding.profilePictureImageView.setImageURI(googleAccount.photoUrl)
            binding.profileEmailTextView.text = googleAccount.email
        }

        binding.profileSignOutButton.setOnClickListener {
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
