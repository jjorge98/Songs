package br.iesb.songs.views.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import br.iesb.songs.R
import br.iesb.songs.data_class.Music
import br.iesb.songs.view_model.DeezerViewModel
import br.iesb.songs.views.ArtistsActivity
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.fragments.SelectPlaylistDialogFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.music_adapter.view.*


class MusicAdapter(
    private val context: Context,
    var musicSet: MutableList<Music>,
    private val activity: FragmentActivity?,
    private val viewModel: DeezerViewModel,
    private val menuType: String,
    principalView: PrincipalActivity?,
    artistView: ArtistsActivity?
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {
    private var verify: String = ""
    private val manager =
        principalView?.supportFragmentManager ?: artistView?.supportFragmentManager
    private val thisActivity = if (principalView != null) {
        "principal"
    } else {
        "artist"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.music_adapter, parent, false)
        return MusicViewHolder(view)
    }

    override fun getItemCount(): Int {
        return musicSet.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = musicSet[position]

        holder.song.text = activity?.getString(R.string.holderSong, music.title, music.artist)
        holder.itemView.setOnClickListener {
            manager?.findFragmentByTag("newPlaylist")?.let { it1 ->
                manager.beginTransaction().remove(
                    it1
                ).commit()
            }

            if (music.id != null) {
                viewModel.verifyPlaylist(music.id, "favorites") {
                    verify = it
                }
            }
            showPopup(holder, music)
        }

        Picasso.get().load(music.coverImg).into(holder.cover)
    }

    private fun showPopup(holder: MusicViewHolder, music: Music) {
        val popup = PopupMenu(context, holder.itemView)
        val inflater: MenuInflater = popup.menuInflater

        if (menuType == "SEARCH") {
            inflater.inflate(R.menu.pop_up_search, popup.menu)
        } else if (menuType == "FAVORITE") {
            inflater.inflate(R.menu.pop_up_favorite, popup.menu)
        } else {
            inflater.inflate(R.menu.pop_up_search, popup.menu)
        }

        popup.setOnMenuItemClickListener { itemSelected ->
            if (itemSelected?.itemId == R.id.removeFavorite) {
                viewModel.removeFavorite(music.id)

                Handler().postDelayed({
                    musicSet.remove(music)
                    notifyDataSetChanged()
                }, 2000)

                return@setOnMenuItemClickListener true
            } else if (itemSelected?.itemId == R.id.favoriteSong) {
                if (verify != "exists") {
                    viewModel.addPlaylist(music, "favorites") { response ->
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Essa música já está nos favoritos!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@setOnMenuItemClickListener true
            } else if (itemSelected?.itemId == R.id.listenDeezer) {
                if (music.link != null) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(music.link))
                    context.startActivity(intent)
                } else {
                    Toast.makeText(
                        context.applicationContext,
                        "Não foi possível abrir o Deezer!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@setOnMenuItemClickListener true
            } else if (itemSelected?.itemId == R.id.addPlaylist) {

                val playlistFragment = SelectPlaylistDialogFragment(manager, music, thisActivity)

                val transaction = manager?.beginTransaction()
                transaction?.add(playlistFragment, "playlistFragment")
                transaction?.commit()

                return@setOnMenuItemClickListener true
            } else {
                val intent = Intent(context.applicationContext, ArtistsActivity::class.java)
                intent.putExtra("artist", music.artist)
                intent.putExtra("artistID", music.artistID.toString())
                context.startActivity(intent)
                return@setOnMenuItemClickListener true
            }
        }
        popup.show()
    }

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val song: TextView = itemView.song
        val cover: ImageView = itemView.coverSong
    }
}