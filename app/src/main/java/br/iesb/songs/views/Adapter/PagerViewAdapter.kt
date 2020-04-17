package br.iesb.songs.views.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.iesb.songs.views.Fragments.FavoritosFragment
import br.iesb.songs.views.Fragments.HomeFragment
import br.iesb.songs.views.Fragments.PesquisaFragment

internal class PagerViewAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                FavoritosFragment()
            }
            2 -> {
                PesquisaFragment()
            }
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 3;
    }


}