package br.iesb.songs.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import br.iesb.songs.R
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.views.adapter.PagerViewAdapter
import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {
    private lateinit var mPagerAdapter: PagerAdapter

    private val viewModelL: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        favoritoBtn.setOnClickListener {
            dismissFragments()
            mViewPager.currentItem = 0
        }
        pesquisaBtn.setOnClickListener {
            dismissFragments()
            mViewPager.currentItem = 1
        }
        playlistBtn.setOnClickListener {
            dismissFragments()
            mViewPager.currentItem = 2
        }
        localizacaoBtn.setOnClickListener {
            dismissFragments()
            mViewPager.currentItem = 3
        }
        LogoutBtn.setOnClickListener { logout() }

        mPagerAdapter = PagerViewAdapter(supportFragmentManager, applicationContext, this)
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
                dismissFragments()
                changingTabs(position)
            }
        })

        //default
        mViewPager.currentItem = 0
        favoritoBtn.setImageResource(R.drawable.ic_favorite_menu_clarin)
    }

    private fun dismissFragments() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        val manager = supportFragmentManager

        manager.findFragmentByTag("newPlaylist")?.let { it1 ->
            manager.beginTransaction().remove(
                it1
            ).commit()
        }

        manager.findFragmentByTag("playlistSongs")?.let { it1 ->
            manager.beginTransaction().remove(
                it1
            ).commit()
        }

        manager.findFragmentByTag("sharedFragment")?.let { it1 ->
            manager.beginTransaction().remove(
                it1
            ).commit()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModelL.verifyLogin { result ->
            if (result == 0) {
                val intent = Intent(this, MainActivity::class.java)
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
        } else if (position == 1) {
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu_clarin)
            playlistBtn.setImageResource(R.drawable.ic_playlist_play)
            localizacaoBtn.setImageResource(R.drawable.ic_location_menu)
            LogoutBtn.setImageResource(R.drawable.ic_logout)

        } else if (position == 2) {
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu)
            playlistBtn.setImageResource(R.drawable.ic_playlist_play_clarin)
            localizacaoBtn.setImageResource(R.drawable.ic_location_menu)
            LogoutBtn.setImageResource(R.drawable.ic_logout)
        } else if (position == 3) {
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu)
            playlistBtn.setImageResource(R.drawable.ic_playlist_play)
            localizacaoBtn.setImageResource(R.drawable.ic_location_menu_clarin)
            LogoutBtn.setImageResource(R.drawable.ic_logout)
        }
    }

    private fun logout() {
        viewModelL.signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
