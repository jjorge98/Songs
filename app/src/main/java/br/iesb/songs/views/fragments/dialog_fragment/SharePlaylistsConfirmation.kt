package br.iesb.songs.views.fragments.dialog_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.iesb.songs.R
import br.iesb.songs.data_class.User
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.fragments.SharedFragment
import kotlinx.android.synthetic.main.fragment_share_playlists_confirmation.*

class SharePlaylistsConfirmation(
    private val user: User,
    private val principalView: PrincipalActivity
) :
    DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_playlists_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleSharedPlaylists.text = activity?.getString(R.string.sharedConfirmationTitle, user.name)

        confirmSharedPlaylists.setOnClickListener { shareConfirmed() }
        cancelSharedPlaylists.setOnClickListener { dismiss() }
    }

    private fun shareConfirmed() {
        val manager = principalView.supportFragmentManager

        manager.beginTransaction().add(R.id.backPrincipalActivity, SharedFragment(user, principalView), "sharedFragment")
            .commit()

        dismiss()
    }
}
