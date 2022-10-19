package br.com.suacifra.screens.home

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
import br.com.suacifra.database.DatabaseHelper
import br.com.suacifra.databinding.HomeFragmentBinding
import br.com.suacifra.models.Chords
import br.com.suacifra.models.Cifras
import br.com.suacifra.models.Tones

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var mainActivityContext: MainActivity
    private lateinit var cifrasList: MutableList<Cifras>
    private lateinit var recyclerView: RecyclerView
    private lateinit var cifrasAdapter: RecyclerView.Adapter<CifrasRecyclerViewAdapter.ViewHolder>
    private lateinit var cifrasLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        mainActivityContext = (activity as MainActivity)

        val databaseHelper = DatabaseHelper(mainActivityContext)

        cifrasList = databaseHelper.getAllCifras()

        if (cifrasList.size == 0)
            binding.noCifrasMessage.visibility = View.VISIBLE
        else
            binding.noCifrasMessage.visibility = View.INVISIBLE

        recyclerView = binding.cifrasRecyclerView
        recyclerView.hasFixedSize()

        cifrasLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = cifrasLayoutManager

        cifrasAdapter = CifrasRecyclerViewAdapter(cifrasList, mainActivityContext)
        recyclerView.adapter = cifrasAdapter

        return binding.root
    }

//    private fun fillCifrasArray(): MutableList<Cifras> {
//        val auxList: MutableList<Cifras> = mutableListOf()
//        val cifra0 = Cifras(
//            0, "Note 0", "Note Body 0", "Billy Jean", mutableListOf(
//                mutableListOf(Tones.A, Tones.E),
//                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
//                mutableListOf(Tones.E, Tones.B),
//            )
//        )
//        val cifra1 = Cifras(
//            0, "Note 0", "Note Body 0", "Billy Jean", mutableListOf(
//                mutableListOf(Tones.A, Tones.E),
//                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
//                mutableListOf(Tones.E, Tones.B),
//            )
//        )
//        val cifra2 = Cifras(
//            0, "Note 0", "Note Body 0", "Billy Jean", mutableListOf(
//                mutableListOf(Tones.A, Tones.E),
//                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
//                mutableListOf(Tones.E, Tones.B),
//            )
//        )
//        val cifra3 = Cifras(
//            0, "Note 0", "Note Body 0", "Billy Jean", mutableListOf(
//                mutableListOf(Tones.A, Tones.E),
//                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
//                mutableListOf(Tones.E, Tones.B),
//            )
//        )
//        val cifra4 = Cifras(
//            0, "Note 0", "Note Body 0", "Billy Jean", mutableListOf(
//                mutableListOf(Tones.A, Tones.E),
//                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
//                mutableListOf(Tones.E, Tones.B),
//            )
//        )
//        val cifra5 = Cifras(
//            0, "Note 0", "Note Body 0", "Billy Jean", mutableListOf(
//                mutableListOf(Tones.A, Tones.E),
//                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
//                mutableListOf(Tones.E, Tones.B),
//            )
//        )
//        val cifra6 = Cifras(
//            0, "Note 0", "Note Body 0", "Billy Jean", mutableListOf(
//                mutableListOf(Tones.A, Tones.E),
//                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
//                mutableListOf(Tones.E, Tones.B),
//            )
//        )
//        val cifra7 = Cifras(
//            0, "Note 0", "Note Body 0", "Billy Jean", mutableListOf(
//                mutableListOf(Tones.A, Tones.E),
//                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
//                mutableListOf(Tones.E, Tones.B),
//            )
//        )
//        val cifra8 = Cifras(
//            0, "Note 0", "Note Body 0", "Billy Jean", mutableListOf(
//                mutableListOf(Tones.A, Tones.E),
//                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
//                mutableListOf(Tones.E, Tones.B),
//            )
//        )
//        val cifra9 = Cifras(
//            0, "Note 0", "Note Body 0", "Billy Jean", mutableListOf(
//                mutableListOf(Tones.A, Tones.E),
//                mutableListOf(Tones.G, Tones.D, Tones.E + Chords.minor, Tones.C),
//                mutableListOf(Tones.E, Tones.B),
//            )
//        )
//
//        auxList.addAll(
//            mutableListOf(
//                cifra0,
//                cifra1,
//                cifra2,
//                cifra3,
//                cifra4,
//                cifra5,
//                cifra6,
//                cifra7,
//                cifra8,
//                cifra9
//            )
//        )
//
//        return auxList
//    }

}
