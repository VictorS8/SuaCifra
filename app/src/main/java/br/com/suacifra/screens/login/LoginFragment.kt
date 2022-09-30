package br.com.suacifra.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.R
import br.com.suacifra.databinding.LoginFragmentBinding
import br.com.suacifra.databinding.MainActivityBinding
import br.com.suacifra.screens.settings.SettingsFragment

class LoginFragment : Fragment() {

//    private lateinit var googleSignInClient: GoogleSignInClient

//    val Req_Code: Int = 123
//    val firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var binding: LoginFragmentBinding
    private lateinit var mainActivityBinding: MainActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        mainActivityBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_activity, container, false)

        binding.signInWithGoogleButton.setOnClickListener {
//            signInGoogle()
        }

//        R.string.default_web_client_id.toString()
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getPrivateKey())
//            .requestEmail()
//            .requestProfile()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(MainActivity(), gso)

        binding.loginGoBackButton.setOnClickListener {
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

//    private fun getPrivateKey(): String {
//        val jsonObject = JSONTokener("google-services.json").nextValue() as JSONObject
//
//        val jsonArray = jsonObject.getJSONArray("client")
//
//        var key = ""
//
//        for (i in 0 until jsonArray.length()) {
//            val jsonKey = jsonArray.getJSONObject(i).getJSONObject("oauth_client")
//
//            key = jsonKey.getJSONArray("oauth_client").getJSONObject(i).getJSONObject("client_id")
//                .toString()
//        }
//
//        return key
//    }
//
//    private fun signInGoogle() {
//        val signInIntent : Intent = googleSignInClient.signInIntent
//
//    }

}
