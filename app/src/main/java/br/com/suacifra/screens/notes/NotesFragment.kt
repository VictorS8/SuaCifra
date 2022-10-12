package br.com.suacifra.screens.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.AboutFragmentBinding
import br.com.suacifra.databinding.NotesFragmentBinding
import br.com.suacifra.screens.settings.SettingsFragment

class NotesFragment : Fragment() {

    private lateinit var binding: NotesFragmentBinding
    private lateinit var mainActivityContext: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.notes_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        binding.notesGoBackButton.setOnClickListener {
            mainActivityContext.addToBackStackFragment(SettingsFragment())
        }

        return binding.root
    }

}
