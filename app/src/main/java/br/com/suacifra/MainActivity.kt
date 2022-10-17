package br.com.suacifra

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.databinding.MainActivityBinding
import br.com.suacifra.screens.add.AddFragment
import br.com.suacifra.screens.home.HomeFragment
import br.com.suacifra.screens.settings.SettingsFragment
import br.com.suacifra.viewModels.MainActivityViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val viewModel = MainActivityViewModel()

    private lateinit var auth: FirebaseAuth
    private var isGoogleSignInStatusOk: Boolean = false
    private var googleAccount: GoogleSignInAccount? = null
    private lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences(
            getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE
        )
        isGoogleSignInStatusOk = sharedPref.getBoolean(
            getString(R.string.shared_preference_sign_in_boolean_key),
            isGoogleSignInStatusOk
        )

        auth = FirebaseAuth.getInstance()
        googleAccount = if (isGoogleSignInStatusOk) {
            GoogleSignIn.getLastSignedInAccount(this)
        } else {
            null
        }
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().requestProfile().build()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        replaceFragment(HomeFragment())

        binding.mainBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeBottomNavigation -> {
                    addToBackStackFragmentOnBottomNavigation(HomeFragment(), HOME_FRAGMENT)
                }
                R.id.addBottomNavigation -> {
                    val sharedPrefEditor = sharedPref.edit()
                    sharedPrefEditor.putBoolean(
                        getString(R.string.shared_preference_edit_cifra_mode_boolean_key),
                        false
                    )
                    sharedPrefEditor.apply()
                    addToBackStackFragmentOnBottomNavigation(AddFragment(), ADD_FRAGMENT)
                }
                R.id.settingsBottomNavigation -> {
                    addToBackStackFragmentOnBottomNavigation(SettingsFragment(), SETTINGS_FRAGMENT)
                }
                else -> {}
            }
            true
        }
    }

    fun getGoogleSignInOptionsOnActivity(): GoogleSignInOptions {
        return googleSignInOptions
    }

    fun getLastSignedInAccountOnActivity(): GoogleSignInAccount? {
        return if (googleAccount != null) {
            googleAccount
        } else {
            null
        }
    }

    fun signInOnActivity() {
        googleAccount = GoogleSignIn.getLastSignedInAccount(this)
    }

    fun signOutOnActivity() {
        auth.signOut()
        googleAccount = null
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out
        )
        fragmentTransaction.replace(binding.mainFrameLayout.id, fragment)
        fragmentTransaction.commit()
    }

    fun addToBackStackFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out
        )
        fragmentTransaction.replace(binding.mainFrameLayout.id, fragment)
        fragmentTransaction.addToBackStack("Add to back stack")
        fragmentTransaction.commit()
    }

    private fun addToBackStackFragmentOnBottomNavigation(fragment: Fragment, fragmentName: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out
        )
        fragmentTransaction.replace(binding.mainFrameLayout.id, fragment)
        fragmentTransaction.addToBackStack(fragmentName)
        fragmentTransaction.commit()
    }

    companion object {
        const val HOME_FRAGMENT = "Home"
        const val ADD_FRAGMENT = "Add"
        const val SETTINGS_FRAGMENT = "Settings"
    }

}
