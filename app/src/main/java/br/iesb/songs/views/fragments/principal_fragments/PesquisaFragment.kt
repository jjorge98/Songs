package br.iesb.songs.views.fragments.principal_fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.view_model.DeezerViewModel
import br.iesb.songs.view_model.PlaylistViewModel
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.adapter.MusicAdapter
import kotlinx.android.synthetic.main.fragment_pesquisa.*

class PesquisaFragment(context: Context, private val principalView: PrincipalActivity) :
    Fragment() {
    private val viewModelD: DeezerViewModel by lazy {
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }

    private val viewModelP: PlaylistViewModel by lazy {
        ViewModelProvider(this).get(PlaylistViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesquisa, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()

        searchButton.setOnClickListener { searchList() }

        backSearchFragment.setOnTouchListener { _, _ ->
            val inputMethodManager: InputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        }
    }

    private fun initRecyclerView() {
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = this.context?.let {
            MusicAdapter(
                it,
                mutableListOf(),
                activity,
                viewModelP,
                "SEARCH",
                null,
                null,
                principalView,
                null
            )
        }
        searchRecyclerView.adapter = adapter

        viewModelD.musicSet.observe(viewLifecycleOwner, Observer { music ->
            adapter?.musicSet?.clear()
            adapter?.musicSet = music.toMutableList()
            adapter?.notifyDataSetChanged()

            if (adapter?.itemCount == 0) {
                textToGoSearch.visibility = View.VISIBLE
            } else {
                textToGoSearch.visibility = View.GONE
            }

        })
    }

    private fun searchList() {
        val find: String = inputSearch.text.toString()

        viewModelD.search(find)
        Toast.makeText(this.context, "Buscando...", Toast.LENGTH_LONG).show()
    }
}
