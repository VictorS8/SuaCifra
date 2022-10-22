package br.com.suacifra

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.databinding.MainActivityBinding
import br.com.suacifra.screens.add.AddFragment
import br.com.suacifra.screens.home.HomeFragment
import br.com.suacifra.screens.settings.SettingsFragment
import br.com.suacifra.utils.Config
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
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )
        isGoogleSignInStatusOk = sharedPref.getBoolean(
            Config.SHARED_PREFERENCE_SIGN_IN_BOOLEAN_KEY,
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
                    sharedPrefEditor.putStringSet(
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_SEQUENCE_SET_STRING_KEY,
                        mutableSetOf()
                    )
                    sharedPrefEditor.putString(
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_TONE_STRING_KEY,
                        null
                    )
                    sharedPrefEditor.putString(
                        Config.SHARED_PREFERENCE_ADD_CIFRA_MODE_NAME_EDIT_TEXT_KEY,
                        ""
                    )
                    sharedPrefEditor.putString(
                        Config.SHARED_PREFERENCE_ADD_CIFRA_MODE_SINGER_NAME_EDIT_TEXT_KEY,
                        ""
                    )
                    sharedPrefEditor.putBoolean(
                        Config.SHARED_PREFERENCE_EDIT_CIFRA_MODE_BOOLEAN_KEY,
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

    val fragmentManager = supportFragmentManager
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out
        )
        fragmentTransaction.replace(binding.mainFrameLayout.id, fragment)
        fragmentTransaction.commit()
    }

    fun addToBackStackFragment(fragment: Fragment) {
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

    fun toastMessage(message: String, duration: Int) {
        Toast.makeText(
            this,
            message,
            duration
        )
            .show()
    }

    fun toastMessage(@StringRes resId: Int, duration: Int) {
        Toast.makeText(
            this,
            getString(resId),
            duration
        )
            .show()
    }

    fun toastMessage(@StringRes resId: Int, formatArgs: Any, duration: Int) {
        Toast.makeText(
            this,
            getString(resId, formatArgs),
            duration
        )
            .show()
    }

}
