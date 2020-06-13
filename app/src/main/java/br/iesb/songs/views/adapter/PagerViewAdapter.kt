package br.iesb.songs.views.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.fragments.FavoritosFragment
import br.iesb.songs.views.fragments.LocationFragment
import br.iesb.songs.views.fragments.PesquisaFragment
import br.iesb.songs.views.fragments.PlaylistFragment

internal class PagerViewAdapter(
    fm: FragmentManager,
    private val context: Context,
    private val view: PrincipalActivity
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavoritosFragment(context, view)
            }
            1 -> {
                PesquisaFragment(context, view)
            }
            2 -> {
                PlaylistFragment(context)
            }
            3 -> {
                LocationFragment(context)
            }
            else -> FavoritosFragment(context, view)
        }
    }

    override fun getCount(): Int {
        return 4;
    }


}