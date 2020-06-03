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
import kotlinx.android.synthetic.main.activity_principal.*

class PrincipalActivity : AppCompatActivity() {
    private lateinit var mPagerAdapter: PagerAdapter
    private lateinit var webIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        homeBtn.setOnClickListener { mViewPager.currentItem = 0 }
        favoritoBtn.setOnClickListener { mViewPager.currentItem = 1 }
        pesquisaBtn.setOnClickListener { mViewPager.currentItem = 2 }

        mPagerAdapter = PagerViewAdapter(supportFragmentManager, applicationContext)
        mViewPager.adapter = mPagerAdapter
        mViewPager.offscreenPageLimit = 3

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
        homeBtn.setImageResource(R.drawable.ic_home_menu_clarin)
    }

    private fun changingTabs(position: Int) {
        if (position == 0) {
            homeBtn.setImageResource(R.drawable.ic_home_menu_clarin)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu)
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu)
        }
        if (position == 1) {
            homeBtn.setImageResource(R.drawable.ic_home_menu)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu)
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu_clarin)
        }
        if (position == 2) {
            homeBtn.setImageResource(R.drawable.ic_home_menu)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu_clarin)
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu)
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
}
