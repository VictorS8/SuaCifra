package br.com.suacifra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.databinding.MainActivityBinding
import br.com.suacifra.screens.add.AddFragment
import br.com.suacifra.screens.home.HomeFragment
import br.com.suacifra.screens.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.mainFrameLayout.id, fragment)
        fragmentTransaction.commit()
    }

}
