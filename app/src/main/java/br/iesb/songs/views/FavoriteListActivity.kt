package br.iesb.songs.views

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
import kotlinx.android.synthetic.main.activity_favorite_list.*

class FavoriteListActivity : AppCompatActivity() {
    private val viewModelD: DeezerViewModel by lazy {
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }

    private val viewModelL: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)

        searchFloatingFavList.setOnClickListener { search() }
        logOutFloatingFavList.setOnClickListener { logout() }

        initRecyclerView()
        favoritesList()
    }

    override fun onResume() {
        super.onResume()
        viewModelL.verifyLogin { result ->
            if (result == 0) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initRecyclerView() {
        favoritesRecyclerViewFavList.layoutManager = LinearLayoutManager(this)
    }

    private fun favoritesList() {
        viewModelD.allFavorites.observe(this, Observer { music ->
            val adapter =
                MusicAdapter(this, music, this, viewModelD, "FAVORITE")
            favoritesRecyclerViewFavList.adapter = adapter
        })

        viewModelD.favoritesList()
    }

    private fun search() {
        val intent = Intent(this, SearchListActivity::class.java)
        startActivity(intent)
    }

    private fun logout() {
        viewModelL.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
