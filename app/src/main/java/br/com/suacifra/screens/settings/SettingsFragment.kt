package br.com.suacifra.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.R
import br.com.suacifra.databinding.MainActivityBinding
import br.com.suacifra.databinding.SettingsFragmentBinding
import br.com.suacifra.screens.login.LoginFragment


class SettingsFragment : Fragment() {

    private lateinit var binding: SettingsFragmentBinding
    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        mainActivityBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_activity, container, false)

//        binding.profilePictureImageView.setOnClickListener {
//            // TODO - Implement it
//        }
//
//        val profileUsernameEditText = binding.profileUsernameEditText
//        val profileUsernameTextView = binding.profileUsernameTextView
//
//        profileUsernameEditText.setOnEditorActionListener { _, i, _ ->
//            if (i == EditorInfo.IME_ACTION_DONE) {
//                profileUsernameEditText.visibility = View.INVISIBLE
//                profileUsernameTextView.text = profileUsernameEditText.text
//                profileUsernameEditText.hideKeyboard()
//                return@setOnEditorActionListener true
//            }
//            return@setOnEditorActionListener false
//        }
//
//        profileUsernameTextView.setOnClickListener {
//
//        }

        binding.tunerButton.setOnClickListener {
            // TODO - Implement it
        }

        binding.loginButton.setOnClickListener {
            replaceFragment(LoginFragment())
        }

        binding.aboutButton.setOnClickListener {
            // TODO - Implement it
        }

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out
        )
        fragmentTransaction.replace(mainActivityBinding.mainFrameLayout.id, fragment)
        fragmentTransaction.commit()
    }

//    private fun View.hideKeyboard() {
//        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(windowToken, 0)
//    }

}
