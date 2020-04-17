package br.iesb.songs.views.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.iesb.songs.views.Fragments.FavoritosFragment
import br.iesb.songs.views.Fragments.HomeFragment
import br.iesb.songs.views.Fragments.PesquisaFragment

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