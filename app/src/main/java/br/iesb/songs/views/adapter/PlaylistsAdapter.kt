package br.iesb.songs.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.iesb.songs.R
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.fragments.dialog_fragment.DeleteConfirmationDialogFragment
import br.iesb.songs.views.fragments.PlaylistSongsFragment
import kotlinx.android.synthetic.main.playlist_adapter.view.*

class PlaylistsAdapter(var playlists: MutableList<String>, private val view: PrincipalActivity) :
    RecyclerView.Adapter<PlaylistsAdapter.PlaylistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_adapter, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = playlists[position]

        holder.name.text = playlist
        holder.itemView.setOnClickListener { showPlaylistSongs(playlist) }
        holder.deleteButton.setOnClickListener { deletePlaylist(playlist) }
    }

    private fun deletePlaylist(name: String) {
        val fragment =
            DeleteConfirmationDialogFragment(
                name,
                this
            )
        val manager1 = view.supportFragmentManager
        val transaction1 = manager1.beginTransaction()
        transaction1.add(fragment, "confirmationFragment")
        transaction1.commit()
    }

    private fun showPlaylistSongs(playlist: String) {
        val manager = view.supportFragmentManager
        manager.beginTransaction()
            .add(R.id.backPrincipalActivity, PlaylistSongsFragment(playlist, view), "playlistSongs")
            .commit()
    }

    class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.namePlaylistAdapter
        val deleteButton: ImageView = itemView.deletePlaylistAdapter
    }
}
