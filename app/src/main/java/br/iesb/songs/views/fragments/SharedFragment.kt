package br.iesb.songs.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.data_class.User
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.view_model.PlaylistViewModel
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.adapter.MusicAdapter
import kotlinx.android.synthetic.main.fragment_shared.*

class SharedFragment(private val user: User, private val principalView: PrincipalActivity) :
    Fragment() {
    private val viewModelL: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private val viewModelP: PlaylistViewModel by lazy {
        ViewModelProvider(this).get(PlaylistViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shared, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dismissSharedFragment.setOnClickListener { dismiss() }

        initRecyclerView()
        spinnerFill()
    }

    private fun spinnerFill() {
        val adapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    listOf("Seus favoritos", "Favoritos de ${user.name}")
                )
            }

        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerShareFragment.adapter = adapter

        spinnerShareFragment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val optionSelected = parent?.getItemAtPosition(position).toString()

                if (optionSelected == "Seus favoritos") {
                    viewModelP.sharedFavorites(null)
                } else {
                    viewModelP.sharedFavorites(user)
                }
            }
        }
    }

    private fun initRecyclerView() {
        val adapter = context?.let {
            MusicAdapter(
                it,
                mutableListOf(),
                activity,
                viewModelP,
                "SHARE",
                null,
                principalView,
                null
            )
        }
        recyclerViewShareFragment.layoutManager = LinearLayoutManager(context)
        recyclerViewShareFragment.adapter = adapter

        viewModelP.favorites.observe(viewLifecycleOwner, Observer { set ->
            adapter?.musicSet = set.toMutableList()
            adapter?.notifyDataSetChanged()
        })
    }

    private fun dismiss() {
        val manager = principalView.supportFragmentManager

        manager.findFragmentByTag("sharedFragment")?.let { it1 ->
            manager.beginTransaction().remove(
                it1
            ).commit()
        }
    }
}
