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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)

        val googleAccount = GoogleSignIn.getLastSignedInAccount((activity as MainActivity))

        binding.profilePictureImageView.setImageURI(googleAccount!!.photoUrl)

        binding.profileEmailTextView.text = googleAccount.email

        binding.profileGoBackButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                activity as MainActivity,
                "You SignOut on you account!",
                Toast.LENGTH_LONG
            ).show()
            (activity as MainActivity).replaceFragment(LoginFragment())
        }

        binding.profileGoBackButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(SettingsFragment())
        }

        return binding.root
    }

}
