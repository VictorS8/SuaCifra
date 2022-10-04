package br.com.suacifra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.databinding.MainActivityBinding
import br.com.suacifra.screens.add.AddFragment
import br.com.suacifra.screens.home.HomeFragment
import br.com.suacifra.screens.profile.ProfileFragment
import br.com.suacifra.screens.settings.SettingsFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val viewModel = MainActivityViewModel()

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

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
                    replaceFragment(HomeFragment())
                }
                R.id.addBottomNavigation -> {
                    replaceFragment(AddFragment())
                }
                R.id.settingsBottomNavigation -> {
                    replaceFragment(SettingsFragment())
                }
                else -> {}
            }
            true
        }
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

    fun replaceFragmentOnSettings(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out
        )
        fragmentTransaction.replace(binding.mainFrameLayout.id, fragment)
        fragmentTransaction.addToBackStack("Options Screen")
        fragmentTransaction.commit()
    }

}
