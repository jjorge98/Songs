package br.iesb.songs.views.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.data_class.Music
import br.iesb.songs.view_model.DeezerViewModel
import kotlinx.android.synthetic.main.fragment_new_playlist.*

class NewPlaylistFragment(private val setPlaylists: MutableSet<String>, private val music: Music) :
    Fragment() {
    private val viewModelD: DeezerViewModel by lazy {
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_playlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backNewPlaylist.setOnClickListener {}
        backNewPlaylist.setOnTouchListener { _, _ ->
            val inputMethodManager: InputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        }

        dismissNewPlaylist.setOnClickListener { dismiss() }
        saveNewPlaylist.setOnClickListener { newPlaylist() }
    }

    private fun dismiss() {
        val manager = activity?.supportFragmentManager

        manager?.findFragmentByTag("newPlaylist")?.let { it1 ->
            manager.beginTransaction().remove(
                it1
            ).commit()
        }
    }

    private fun newPlaylist() {
        val name = nameNewPlaylist.text.toString()

        viewModelD.newPlaylist(setPlaylists, name) { response ->
            Toast.makeText(this.context, response[1], Toast.LENGTH_SHORT).show()

            if (response[0] == "OK") {
                viewModelD.addPlaylist(music, name) { responseMusicAdded ->
                    Toast.makeText(context, responseMusicAdded, Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }
}
