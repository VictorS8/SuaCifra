package br.com.suacifra.screens.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import br.com.suacifra.MainActivity
import br.com.suacifra.R
import br.com.suacifra.databinding.AddFragmentBinding
import br.com.suacifra.models.Chords
import br.com.suacifra.models.Tones

class AddFragment : Fragment() {

    private lateinit var binding: AddFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private lateinit var sequenceChordsList: MutableList<MutableList<String>>
    private lateinit var recyclerView: RecyclerView
    private lateinit var sequenceAdapter: Adapter<SequenceChordsRecyclerViewAdapter.ViewHolder>
    private lateinit var sequenceLayoutManager: LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.add_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        binding.chooseToneButton.setOnClickListener {
            checkCardViewVisibility()
        }

        // TODO - Change to update list
        sequenceChordsList = fillSequenceChordsArray()

        recyclerView = binding.sequenceRecyclerView
        recyclerView.hasFixedSize()

        sequenceLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = sequenceLayoutManager

        sequenceAdapter = SequenceChordsRecyclerViewAdapter(sequenceChordsList, mainActivityContext)
        recyclerView.adapter = sequenceAdapter

        binding.addSequencesImageButton.setOnClickListener {
            Toast.makeText(
                mainActivityContext,
                "Add one sequence and change screen",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.saveCifraButton.setOnClickListener {
            Toast.makeText(
                mainActivityContext,
                getString(R.string.save_cifra_successfully, binding.cifraNameEditText.text),
                Toast.LENGTH_SHORT
            ).show()
        }

        allToneClickEvent()

        return binding.root
    }

    private fun checkCardViewVisibility() {
        binding.apply {
            chooseToneCardView.visibility =
                if (chooseToneCardView.isVisible) View.GONE else View.VISIBLE
        }
    }

    private fun clickableTextAsTextViewEvent(view: View) {
        val textViewString: TextView = view as TextView
        val returnToneText = getString(R.string.tone_chosen_text, textViewString.text)

        Toast.makeText(context, returnToneText, Toast.LENGTH_LONG).show()
        binding.songToneButtonHelper.text = returnToneText
        checkCardViewVisibility()
    }

    private fun allToneClickEvent() {

        binding.apply {
            aToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            bbToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            bToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            cToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            dbToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            dToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            ebToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            eToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            fToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            gbToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            gToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
            abToneTextView.setOnClickListener { clickableTextAsTextViewEvent(it) }
        }
    }

    private fun fillSequenceChordsArray(): MutableList<MutableList<String>> {
        val auxList: MutableList<MutableList<String>> = mutableListOf()

        auxList.addAll(
            mutableListOf(
                mutableListOf(Tones.A, Tones.E),
                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
                mutableListOf(Tones.E, Tones.B),
                mutableListOf(Tones.F, Tones.C, Tones.D + Chords.minor, Tones.B, Tones.ASharp),
                mutableListOf(Tones.A, Tones.E),
                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
                mutableListOf(Tones.E, Tones.B),
                mutableListOf(Tones.F, Tones.C, Tones.D + Chords.minor, Tones.B, Tones.ASharp),
                mutableListOf(Tones.C, Tones.G),
                mutableListOf(Tones.C, Tones.G, Tones.A + Chords.minor, Tones.F)
            )
        )

        return auxList
    }

}
