package br.iesb.songs.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import br.iesb.songs.R
import br.iesb.songs.views.Adapter.PagerViewAdapter

class PrincipalActivity : AppCompatActivity() {
    private lateinit var mViewPager: ViewPager

    private lateinit var homeBtn:ImageButton
    private lateinit var favoritoBtn:ImageButton
    private lateinit var pesquisaBtn:ImageButton

    private lateinit var mPagerAdapter: PagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)


        //init image buttons
        homeBtn = findViewById(R.id.homeBtn)
        favoritoBtn = findViewById(R.id.favoritoBtn)
        pesquisaBtn = findViewById(R.id.pesquisaBtn)


        mPagerAdapter = PagerViewAdapter(supportFragmentManager)
        mViewPager.adapter = mPagerAdapter
        mViewPager.offscreenPageLimit = 5


        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
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
        if(position == 0){
            homeBtn.setImageResource(R.drawable.ic_home_menu_clarin)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu)
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu)
        }
        if(position == 1){
            homeBtn.setImageResource(R.drawable.ic_home_menu)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu_clarin)
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu)

        }
        if(position == 2){
            homeBtn.setImageResource(R.drawable.ic_home_menu)
            pesquisaBtn.setImageResource(R.drawable.ic_search_menu)
            favoritoBtn.setImageResource(R.drawable.ic_favorite_menu_clarin)

        }
    }
}
