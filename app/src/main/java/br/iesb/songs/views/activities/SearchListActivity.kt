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
import kotlinx.android.synthetic.main.activity_search_list.*

class SearchListActivity : AppCompatActivity() {
    private val viewModelD: DeezerViewModel by lazy {
        ViewModelProvider(this).get(DeezerViewModel::class.java)
    }

    private val viewModelL: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)

        initRecyclerView()
        buttonSearchList.setOnClickListener { searchList() }

        favoriteFloatingSearchList.setOnClickListener { favorites() }
        logOutFloatingSearchList.setOnClickListener { logout() }
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

    private fun initRecyclerView(){
        recyclerViewSearchList.layoutManager = LinearLayoutManager(this)
    }

    private fun searchList() {
        val find: String = inputSearchList.text.toString()

        viewModelD.musicSet.observe(this, Observer { music ->
            val adapter =
                MusicAdapter(this, music, this, viewModelD, "SEARCH")
            recyclerViewSearchList.adapter = adapter
        })

        viewModelD.search(find)
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
}
