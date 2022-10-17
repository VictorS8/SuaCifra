package br.com.suacifra.screens.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.AddSequenceFragmentBinding

class AddSequenceFragment : Fragment() {

    private lateinit var binding: AddSequenceFragmentBinding
    private lateinit var mainActivityContext: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.add_sequence_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        binding.cancelSequenceImageButton.setOnClickListener {
            Toast.makeText(
                mainActivityContext,
                getString(R.string.add_note_cancelled),
                Toast.LENGTH_LONG
            )
                .show()
            mainActivityContext.replaceFragment(AddFragment())
        }

        binding.addSequenceImageButton.setOnClickListener {
            Toast.makeText(
                mainActivityContext,
                getString(R.string.add_sequence_successfully),
                Toast.LENGTH_LONG
            )
                .show()
            mainActivityContext.replaceFragment(AddFragment())
        }

        return binding.root
    }

}
