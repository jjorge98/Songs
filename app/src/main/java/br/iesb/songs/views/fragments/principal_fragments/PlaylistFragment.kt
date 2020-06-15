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
import br.iesb.songs.view_model.PlaylistViewModel
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.adapter.PlaylistsAdapter
import br.iesb.songs.views.fragments.NewPlaylistFragment
import kotlinx.android.synthetic.main.fragment_playlist.*

class PlaylistFragment(context: Context, private val principalView: PrincipalActivity) :
    Fragment() {
    private val viewModelP: PlaylistViewModel by lazy {
        ViewModelProvider(this).get(PlaylistViewModel::class.java)
    }
    val adapter = PlaylistsAdapter(mutableListOf(), principalView)

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

        floatingAddMusicButton.setOnClickListener { newPlaylist() }
    }

    private fun initRecyclerView() {
        playlistsRecyclerView.layoutManager = LinearLayoutManager(context)
        playlistsRecyclerView.adapter = adapter

        viewModelP.playlists.observe(viewLifecycleOwner, Observer { playlists ->
            adapter.playlists = playlists.toMutableList()
            adapter.notifyDataSetChanged()

            if (adapter.itemCount == 0) {
                textToGoPlayList.visibility = View.VISIBLE
            } else {
                textToGoPlayList.visibility = View.GONE
            }

        })

        viewModelP.showPlaylists()
    }

    private fun newPlaylist() {
        val manager = principalView.supportFragmentManager

        manager.beginTransaction().add(
            R.id.backPrincipalActivity,
            NewPlaylistFragment(adapter.playlists.toMutableSet(), null),
            "newPlaylist"
        ).commit()
    }
}
