package br.com.suacifra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.suacifra.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationMenuView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())


        binding.mainBottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeBottomNavigation -> {
                    replaceFragment(Home())
                }
                R.id.addBottomNavigation -> replaceFragment(Add())
                R.id.settingsBottomNavigation -> replaceFragment(Settings())
                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment)
        fragmentTransaction.commit()
    }

}
