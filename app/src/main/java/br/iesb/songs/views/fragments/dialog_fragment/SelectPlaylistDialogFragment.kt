package br.iesb.songs.views.fragments.dialog_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.data_class.Music
import br.iesb.songs.view_model.PlaylistViewModel
import br.iesb.songs.views.fragments.NewPlaylistFragment
import kotlinx.android.synthetic.main.fragment_select_playlist_dialog.*

class SelectPlaylistDialogFragment(
    private val manager: FragmentManager?,
    private val music: Music,
    private val activeActivity: String
) : DialogFragment() {
    private lateinit var playlistName: String
    private var verify: String = ""

    private val viewModelP: PlaylistViewModel by lazy {
        ViewModelProvider(this).get(PlaylistViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_playlist_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleSelectPlaylist.visibility = View.GONE
        spinnerSelectPlaylist.visibility = View.GONE
        saveSelectPlaylist.visibility = View.GONE

        saveSelectPlaylist.setOnClickListener { addPlaylist() }
        cancelSelectPlaylist.setOnClickListener { dismiss() }

        spinnerFill()
    }

    private fun spinnerFill() {
        viewModelP.playlists.observe(viewLifecycleOwner, Observer { playlists ->
            if (playlists.isNotEmpty()) {
                titleSelectPlaylist.visibility = View.VISIBLE
                spinnerSelectPlaylist.visibility = View.VISIBLE
                saveSelectPlaylist.visibility = View.VISIBLE
                noPlaylistSelectPlaylist.visibility = View.GONE

                val adapter =
                    context?.let {
                        ArrayAdapter(
                            it,
                            android.R.layout.simple_spinner_item,
                            playlists.toList()
                        )
                    }
                adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerSelectPlaylist.adapter = adapter
            }

            createSelectPlaylist.setOnClickListener { newPlaylist(playlists) }
        })

        viewModelP.showPlaylists()

        spinnerSelectPlaylist.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                playlistName = parent?.getItemAtPosition(position).toString()
                music.id?.let {
                    viewModelP.verifyPlaylist(it, playlistName) { response ->
                        verify = response
                    }
                }
            }
        }
    }

    private fun newPlaylist(setPlaylists: MutableSet<String>) {
        val id = if (activeActivity == "principal") {
            R.id.backPrincipalActivity
        } else {
            R.id.backArtistActivity
        }

        manager?.beginTransaction()
            ?.add(
                id,
                NewPlaylistFragment(
                    setPlaylists,
                    music
                ), "newPlaylist"
            )
            ?.commit()
        this.dismiss()
    }

    private fun addPlaylist() {
        if (verify != "exists") {
            viewModelP.addPlaylist(music, playlistName) { response ->
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                dismiss()
            }
        } else {
            Toast.makeText(context, "Essa música já está em uma playlist!", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
