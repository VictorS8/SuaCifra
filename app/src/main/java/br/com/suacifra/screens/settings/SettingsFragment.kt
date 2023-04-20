package br.com.suacifra.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.SettingsFragmentBinding
import br.com.suacifra.screens.about.AboutFragment
import br.com.suacifra.screens.notes.NotesFragment


class SettingsFragment : Fragment() {

    private lateinit var binding: SettingsFragmentBinding
    private lateinit var mainActivityContext: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        binding.notesButton.setOnClickListener {
            mainActivityContext.addToBackStackFragment(NotesFragment())
        }

        binding.tunerButton.setOnClickListener {
            // TODO - Implement it
        }

        binding.aboutButton.setOnClickListener {
            mainActivityContext.addToBackStackFragment(AboutFragment())
        }

        return binding.root
    }

}
