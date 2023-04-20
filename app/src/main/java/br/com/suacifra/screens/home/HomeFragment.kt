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
import br.com.suacifra.models.Cifras

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

        if (cifrasList.size == 0) {
            binding.noCifrasMessage.visibility = View.VISIBLE
            binding.appTitle.visibility = View.VISIBLE
        } else {
            binding.noCifrasMessage.visibility = View.GONE
            binding.appTitle.visibility = View.GONE
        }

        recyclerView = binding.cifrasRecyclerView
        recyclerView.hasFixedSize()

        cifrasLayoutManager = LinearLayoutManager(mainActivityContext)
        recyclerView.layoutManager = cifrasLayoutManager

        cifrasAdapter = CifrasRecyclerViewAdapter(cifrasList, mainActivityContext)
        recyclerView.adapter = cifrasAdapter

        return binding.root
    }

}
