package br.iesb.songs.views.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.view_model.DeezerViewModel
import br.iesb.songs.views.Adapter.MusicAdapter
import kotlinx.android.synthetic.main.fragment_pesquisa.*

/**
 * A simple [Fragment] subclass.
 */
class PesquisaFragment (context: Context) : Fragment() {
    private val viewModel: DeezerViewModel by lazy {
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        configureRecyclerView()
        searchList()
        searchButton.setOnClickListener { searchList() }

        return inflater.inflate(R.layout.fragment_pesquisa, container, false)
    }

    private fun configureRecyclerView() {
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun searchList() {
        val find: String = inputSearch.text.toString()

        viewModel.musicSet.observe(viewLifecycleOwner, Observer { music ->
            val adapter = MusicAdapter(music, activity)
            searchRecyclerView.adapter = adapter
        })

        viewModel.search(find)
    }
}
