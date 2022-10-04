package br.com.suacifra.screens.login

import android.app.Activity.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.LoginFragmentBinding
import br.com.suacifra.screens.profile.ProfileFragment
import br.com.suacifra.screens.settings.SettingsFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)

        firebaseAuth = Firebase.auth

        val googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("160413729430-44vfsstlnbqjhoi30kervdnbk1vtpm4i.apps.googleusercontent.com")
                .requestEmail().requestProfile().build()

        googleSignInClient = GoogleSignIn.getClient(activity as MainActivity, googleSignInOptions)

        binding.signInWithGoogleButton.setOnClickListener {
            signInWithGoogleButton()
        }

        binding.loginGoBackButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(SettingsFragment())
        }

        return binding.root
    }

    private fun signInWithGoogleButton() {
        val intent = googleSignInClient.signInIntent
        openActivity.launch(intent)
    }

    private var openActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val intentOnResult = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intentOnResult)
            try {
                val account = task.result
                signInWithGoogle(account.idToken!!)
            } catch (exception: ApiException) {
                Toast.makeText(
                    (activity as MainActivity),
                    getString(R.string.open_activity_error_message, exception),
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                (activity as MainActivity),
                getString(R.string.open_activity_error_message_on_result),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity as MainActivity) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        (activity as MainActivity),
                        getString(R.string.sign_in_with_google_message_success),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    (activity as MainActivity).replaceFragment(ProfileFragment())
                } else {
                    Toast.makeText(
                        (activity as MainActivity),
                        getString(R.string.sign_in_with_google_message_fail),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
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
