package br.iesb.songs.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.view_model.DeezerViewModel
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.views.adapter.MusicAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_artists.*

class ArtistsActivity : AppCompatActivity() {
    private val viewModelD: DeezerViewModel by lazy {
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }
    private val viewModelL: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    private var artist: String? = null
    private var idArtist: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artists)
        val intent = this.intent

        favoriteFloatingArtists.setOnClickListener { favorites() }
        searchFloatingArtists.setOnClickListener { search() }
        logOutFloatingArtists.setOnClickListener { logout() }

        artist = intent.getStringExtra("artist")
        idArtist = intent.getStringExtra("artistID")
        textViewArtist.text = getString(R.string.artist, artist)

        artistSongsRecyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
        songsList()
    }

    override fun onResume() {
        super.onResume()
        viewModelL.verifyLogin { result ->
            if (result == 0) {
                val intent = Intent(this, MainInicialActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun songsList() {
        viewModelD.musicSet.observe(this, Observer { music ->
            if (music.isNotEmpty()) {
                Picasso.get().load(music[0].artistImage).into(artistImage);
            }
            val adapter = MusicAdapter(this, music, this, viewModelD, "SEARCH")
            artistSongsRecyclerView.adapter = adapter
        })

        viewModelD.artist(idArtist?.toInt())
    }

    private fun favorites() {
        val intent = Intent(this, FavoriteListActivity::class.java)
        startActivity(intent)
    }

    private fun logout() {
        viewModelL.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun search() {
        val intent = Intent(this, SearchListActivity::class.java)
        startActivity(intent)
    }
}
