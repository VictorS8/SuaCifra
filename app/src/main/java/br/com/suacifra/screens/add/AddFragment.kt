package br.com.suacifra.screens.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.com.suacifra.R

class AddFragment : Fragment(), View.OnClickListener {

    val toneChosen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_add, container, false)
        val chooseToneButton: Button = view.findViewById(R.id.chooseToneButton)
        val chooseToneCardView: CardView = view.findViewById(R.id.chooseToneCardView)

        val aToneTextView: TextView = view.findViewById(R.id.aToneTextView)
        val bbToneTextView: TextView = view.findViewById(R.id.bbToneTextView)
        val bToneTextView: TextView = view.findViewById(R.id.bToneTextView)
        val cToneTextView: TextView = view.findViewById(R.id.cToneTextView)
        val dbToneTextView: TextView = view.findViewById(R.id.dbToneTextView)
        val dToneTextView: TextView = view.findViewById(R.id.dToneTextView)
        val ebToneTextView: TextView = view.findViewById(R.id.ebToneTextView)
        val eToneTextView: TextView = view.findViewById(R.id.eToneTextView)
        val fToneTextView: TextView = view.findViewById(R.id.fToneTextView)
        val gbToneTextView: TextView = view.findViewById(R.id.gbToneTextView)
        val gToneTextView: TextView = view.findViewById(R.id.gToneTextView)
        val abToneTextView: TextView = view.findViewById(R.id.abToneTextView)

        chooseToneButton.setOnClickListener {
            checkCardViewVisibility()
        }

        aToneTextView.setOnClickListener(this)
        bbToneTextView.setOnClickListener(this)
        bToneTextView.setOnClickListener(this)
        cToneTextView.setOnClickListener(this)
        dbToneTextView.setOnClickListener(this)
        dToneTextView.setOnClickListener(this)
        ebToneTextView.setOnClickListener(this)
        eToneTextView.setOnClickListener(this)
        fToneTextView.setOnClickListener(this)
        gbToneTextView.setOnClickListener(this)
        gToneTextView.setOnClickListener(this)
        abToneTextView.setOnClickListener(this)

        return view
    }

    private fun checkCardViewVisibility() {
        val chooseToneCardView: CardView = requireView().findViewById(R.id.chooseToneCardView)
        chooseToneCardView.visibility =
            if (chooseToneCardView.isVisible) View.GONE else View.VISIBLE
    }

    override fun onClick(view: View?) {
        val textViewString: TextView = view as TextView
        Toast.makeText(context, "Tone chosen: ${textViewString.text}", Toast.LENGTH_LONG).show()
        checkCardViewVisibility()
    }

}
