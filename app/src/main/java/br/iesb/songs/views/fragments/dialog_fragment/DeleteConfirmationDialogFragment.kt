package br.iesb.songs.views.fragments.dialog_fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.DeezerViewModel
import br.iesb.songs.views.adapter.PlaylistsAdapter
import kotlinx.android.synthetic.main.fragment_delete_confirmation_dialog.*

class DeleteConfirmationDialogFragment(
    private val playlist: String,
    private val adapter: PlaylistsAdapter
) : DialogFragment() {
    private val viewModel: DeezerViewModel by lazy {
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_confirmation_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelButtonDeleteConfirmation.setOnClickListener { dismiss() }
        confirmButtonDeleteConfirmation.setOnClickListener { delete() }
    }

    private fun delete() {
        viewModel.deletePlaylist(playlist)

        Handler().postDelayed({
            adapter.playlists.remove(playlist)
            adapter.notifyDataSetChanged()
        }, 1500)

        dismiss()
    }
}
