package br.iesb.songs.views.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.view_model.DeezerViewModel
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.views.MainInicialActivity
import br.iesb.songs.views.adapter.MusicAdapter
import kotlinx.android.synthetic.main.fragment_pesquisa.*

class PesquisaFragment(context: Context) : Fragment() {
    private val viewModel: DeezerViewModel by lazy {
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }

    private val viewModelL: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesquisa, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModelL.verifyLogin { result ->
            if (result == 0) {
                val intent = Intent(context, MainInicialActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()

        searchButton.setOnClickListener { searchList() }
    }

    private fun initRecyclerView() {
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = this.context?.let { MusicAdapter(it, mutableListOf(), activity, viewModel, "SEARCH") }
        searchRecyclerView.adapter = adapter

        viewModel.musicSet.observe(viewLifecycleOwner, Observer { music ->
            adapter?.musicSet?.clear()
            adapter?.musicSet = music.toMutableList()
            adapter?.notifyDataSetChanged()
        })
    }

    private fun searchList() {
        val find: String = inputSearch.text.toString()

        viewModel.search(find)
        Toast.makeText(this.context, "Buscando...", Toast.LENGTH_LONG).show()
    }
}
