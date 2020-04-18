package br.iesb.songs.views.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import br.iesb.songs.R
import br.iesb.songs.data_class.Music
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.music_adapter.view.*

class MusicAdapter(
    private val context: Context,
    private val musicSet: Array<Music>,
    private val activity: FragmentActivity?
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {
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
        holder.song.setOnClickListener {
            showPopup(holder)
        }

        Picasso.get().load(music.coverImg).into(holder.cover);
    }

    private fun showPopup(holder: MusicViewHolder) {
//        val popup = PopupMenu(context, holder.itemView)
//        popup.setOnMenuItemClickListener {  }
//        popup.inflate(R.menu.pop_up_menu)
//        popup.show()

        val popup = PopupMenu(context, holder.itemView)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.pop_up_menu, popup.menu)
        popup.setOnMenuItemClickListener{itemSelected ->
            if(itemSelected?.itemId == R.id.favoriteSong){
                Log.w(TAG, "$holder")
                return@setOnMenuItemClickListener true
            } else{
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
