package br.iesb.songs.views.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import br.iesb.songs.R
import br.iesb.songs.data_class.Music
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.music_adapter.view.*

class MusicAdapter(private val musicSet: Array<Music>, private val activity: FragmentActivity?) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicViewHolder {
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

        Picasso.get().load(music.coverImg).into(holder.cover);
    }

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val song: TextView = itemView.song
        val cover: ImageView = itemView.coverSong
    }
}