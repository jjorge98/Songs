package br.iesb.songs.views.fragments

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.view_model.DeezerViewModel
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.views.adapter.MusicAdapter
import kotlinx.android.synthetic.main.fragment_pesquisa.*
import android.widget.Toast
import br.iesb.songs.views.MainInicialActivity

/**
 * A simple [Fragment] subclass.
 */
class PesquisaFragment (context: Context) : Fragment() {
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
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        searchButton.setOnClickListener { searchList() }
    }

    private fun searchList() {
        val find: String = inputSearch.text.toString()
        viewModel.musicSet.observe(viewLifecycleOwner, Observer { music ->
            Toast.makeText(this.context, "Buscando...", Toast.LENGTH_LONG).show()
            val adapter = this.context?.let { MusicAdapter(it, music, activity, viewModel, "SEARCH") }
            searchRecyclerView.adapter = adapter
        })

        viewModel.search(find)
    }
}
