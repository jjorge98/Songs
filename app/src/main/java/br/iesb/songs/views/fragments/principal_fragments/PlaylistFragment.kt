package br.iesb.songs.views.fragments.principal_fragments

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
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.adapter.PlaylistsAdapter
import kotlinx.android.synthetic.main.fragment_playlist.*

class PlaylistFragment(context: Context, private val principalView: PrincipalActivity) : Fragment() {
    private val viewModelD: DeezerViewModel by lazy {
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView(){
        val adapter = PlaylistsAdapter(mutableListOf(), principalView)
        playlistsRecyclerView.layoutManager = LinearLayoutManager(context)
        playlistsRecyclerView.adapter = adapter

        viewModelD.playlists.observe(viewLifecycleOwner, Observer {playlists ->
            adapter.playlists = playlists.toMutableList()
            adapter.notifyDataSetChanged()
        })

        viewModelD.showPlaylists()
    }
}
