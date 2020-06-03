package br.iesb.songs.views.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.iesb.songs.views.fragments.FavoritosFragment
import br.iesb.songs.views.fragments.HomeFragment
import br.iesb.songs.views.fragments.PesquisaFragment

internal class PagerViewAdapter(fm: FragmentManager?, private val context: Context) :
    FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment(context)
            }
            1 -> {
                FavoritosFragment(context)
            }
            2 -> {
                PesquisaFragment(context)
            }
            else -> HomeFragment(context)
        }
    }

    override fun getCount(): Int {
        return 3;
    }


}