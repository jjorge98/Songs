package br.iesb.songs.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.view_model.DeezerViewModel
import br.iesb.songs.views.adapter.MusicAdapter
import br.iesb.songs.views.fragments.FavoritosFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_artists.*

class ArtistsActivity : AppCompatActivity() {

    private val viewModel: DeezerViewModel by lazy {
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

        initRecyclerView()
        songsList()
        BackListaFavoritosFloating.setOnClickListener { backListFavoritos() }
    }

    private fun initRecyclerView() {
        val adapter = MusicAdapter(this, mutableListOf(), this, viewModel, "SEARCH", null, this)

        artistSongsRecyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
        artistSongsRecyclerView.adapter = adapter

        viewModel.musicSet.observe(this, Observer { music ->
            val newMusic = music.toMutableList()
            newMusic.forEach{
                Log.w("TAG", "$it")
            }
            if (music.isNotEmpty()) {
                Picasso.get().load(newMusic[0].artistImage).into(artistImage)
            }

            adapter.musicSet.clear()
            adapter.musicSet = newMusic
            adapter.notifyDataSetChanged()
        })
    }

    private fun songsList() {
        viewModel.artist(idArtist?.toInt())
    }

    private fun backListFavoritos() {
        val intentMain = Intent(this, PrincipalActivity::class.java)
        startActivity(intentMain)
    }
}
