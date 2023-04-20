package br.com.suacifra.screens.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.AddCifraSequenceFragmentBinding
import br.com.suacifra.utils.Config

class AddCifraSequenceFragment : Fragment() {

    private lateinit var binding: AddCifraSequenceFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private lateinit var recyclerView: RecyclerView
    private lateinit var sequenceAdapter: RecyclerView.Adapter<SequenceChordsRecyclerViewAdapter.ViewHolder>
    private lateinit var sequenceLayoutManager: RecyclerView.LayoutManager

    private var cifraSequenceSetString: MutableSet<String> = mutableSetOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_cifra_sequence_fragment,
            container,
            false
        )

        mainActivityContext = (activity as MainActivity)

        val sharedPref = mainActivityContext.getSharedPreferences(
            Config.SHARED_PREFERENCE_FILE_KEY, Context.MODE_PRIVATE
        )

        cifraSequenceSetString = sharedPref.getStringSet(
            Config.SHARED_PREFERENCE_CIFRA_SEQUENCE_STRING_KEY,
            cifraSequenceSetString
        ) ?: mutableSetOf()

        if (cifraSequenceSetString.size == 0)
            binding.noSequenceMessage.visibility = View.VISIBLE
        else
            binding.noSequenceMessage.visibility = View.GONE

        recyclerView = binding.sequenceRecyclerView
        recyclerView.hasFixedSize()

        sequenceLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = sequenceLayoutManager

        sequenceAdapter = SequenceChordsRecyclerViewAdapter(
            cifraSequenceSetString,
            mainActivityContext
        )
        recyclerView.adapter = sequenceAdapter

        binding.addCifraSequenceAddSequenceImageButton.setOnClickListener {
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putBoolean(
                Config.SHARED_PREFERENCE_SEQUENCE_BOOLEAN_KEY,
                false
            )
            sharedPrefEditor.apply()
            mainActivityContext.addToBackStackFragment(AddSequenceFragment())
        }

        binding.addCifraSequenceBackImageButton.setOnClickListener {
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putStringSet(
                Config.SHARED_PREFERENCE_CIFRA_SEQUENCE_STRING_KEY,
                cifraSequenceSetString
            )
            sharedPrefEditor.apply()
            mainActivityContext.popBackStackFragment()
        }

        binding.addCifraSequenceNextImageButton.setOnClickListener {
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putStringSet(
                Config.SHARED_PREFERENCE_CIFRA_SEQUENCE_STRING_KEY,
                cifraSequenceSetString
            )
            sharedPrefEditor.apply()
            mainActivityContext.addToBackStackFragment(AddCifraOverviewFragment())
        }

        return binding.root
    }

}