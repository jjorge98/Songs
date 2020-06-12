package br.iesb.songs.views

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import br.iesb.songs.R
import br.iesb.songs.data_class.Music
import br.iesb.songs.views.adapter.PagerViewAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.view_model.LoginViewModel
import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {
    private lateinit var mPagerAdapter: PagerAdapter
    private lateinit var webIntent: Intent

    private val viewModelL: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        favoritoBtn.setOnClickListener { mViewPager.currentItem = 0 }
        pesquisaBtn.setOnClickListener { mViewPager.currentItem = 1 }
        localizacaoBtn.setOnClickListener { mViewPager.currentItem = 2 }
        LogoutBtn.setOnClickListener { logout() }

        mPagerAdapter = PagerViewAdapter(supportFragmentManager, applicationContext)
        mViewPager.adapter = mPagerAdapter
        mViewPager.offscreenPageLimit = 4

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
            override fun onPageSelected(position: Int) {
                changingTabs(position)
            }
        })

        //default
        mViewPager.currentItem = 0
        favoritoBtn.setImageResource(R.drawable.ic_favorite_menu_clarin)
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

    private fun changingTabs(position: Int) {
        if (position == 0) {
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu_clarin)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu)
            playlistBtn.setImageResource(R.drawable.ic_playlist_play)
            localizacaoBtn.setImageResource(R.drawable.ic_location_menu)
            LogoutBtn.setImageResource(R.drawable.ic_logout)

        }
        if (position == 1) {
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu_clarin)
            playlistBtn.setImageResource(R.drawable.ic_playlist_play)
            localizacaoBtn.setImageResource(R.drawable.ic_location_menu)
            LogoutBtn.setImageResource(R.drawable.ic_logout)

        }

        if (position == 2) {
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu)
            playlistBtn.setImageResource(R.drawable.ic_playlist_play_clarin)
            localizacaoBtn.setImageResource(R.drawable.ic_location_menu)
            LogoutBtn.setImageResource(R.drawable.ic_logout)
        }
        if (position == 3) {
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu)
            playlistBtn.setImageResource(R.drawable.ic_playlist_play)
            localizacaoBtn.setImageResource(R.drawable.ic_location_menu_clarin)
            LogoutBtn.setImageResource(R.drawable.ic_logout)
        }
    }

    fun implicitIntent(link: String){
        webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(webIntent)
    }

    fun directArtist(music: Music){
        val intent = Intent(this, ArtistsActivity::class.java)
        intent.putExtra("artistName", music.artist)
        intent.putExtra("artistID", music.artistID)
        startActivity(intent)
    }

    private fun logout() {
        viewModelL.signOut()
        val intent = Intent(this, MainInicialActivity::class.java)
        startActivity(intent)
    }
}
