package br.iesb.songs.views.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.iesb.songs.views.fragments.FavoritosFragment
import br.iesb.songs.views.fragments.LocationFragment
import br.iesb.songs.views.fragments.PesquisaFragment

internal class PagerViewAdapter(fm: FragmentManager?, private val context: Context) :
    FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavoritosFragment(context)
            }
            1 -> {
                PesquisaFragment(context)
            }
            2 -> {
                LocationFragment(context)
            }
            else -> FavoritosFragment(context)
        }
    }

    override fun getCount(): Int {
        return 3;
    }


}