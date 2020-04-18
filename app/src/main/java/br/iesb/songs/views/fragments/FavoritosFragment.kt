package br.iesb.songs.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.view_model.DeezerViewModel
import br.iesb.songs.views.adapter.MusicAdapter
import kotlinx.android.synthetic.main.fragment_favoritos.*

/**
 * A simple [Fragment] subclass.
 */
class FavoritosFragment(context: Context) : Fragment() {
    private val viewModel: DeezerViewModel by lazy {
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        favoritesList()
    }

    private fun favoritesList() {
        viewModel.allFavorites.observe(viewLifecycleOwner, Observer { music ->
            val adapter = this.context?.let { MusicAdapter(it, music, activity, viewModel, "FAVORITE") }
            favoritesRecyclerView.adapter = adapter
        })

        viewModel.favoritesList()
    }
}
