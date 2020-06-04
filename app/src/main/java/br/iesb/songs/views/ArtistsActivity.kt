package br.iesb.songs.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.view_model.DeezerViewModel
import br.iesb.songs.views.adapter.MusicAdapter
import kotlinx.android.synthetic.main.activity_artists.*
import com.squareup.picasso.Picasso


class ArtistsActivity : AppCompatActivity() {

    private val viewModel: DeezerViewModel by lazy{
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }

    private var artist: String? = null
    private var idArtist: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artists)
        val intent = this.intent

        artist = intent.getStringExtra("artist")
        idArtist = intent.getStringExtra("artistID")
        textViewArtist.text = getString(R.string.artist, artist)

        artistSongsRecyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
        songsList()
    }

    private fun songsList(){
        viewModel.musicSet.observe(this, Observer { music ->
            if (music.isNotEmpty()) {
                Picasso.get().load(music[0].artistImage).into(artistImage);
            }
            val adapter = MusicAdapter(this, music, this, viewModel, "SEARCH")
            artistSongsRecyclerView.adapter = adapter
        })

        viewModel.artist(idArtist?.toInt())
    }
}
