package br.iesb.songs.repository

import android.content.Context
import br.iesb.songs.data_class.Music
import br.iesb.songs.data_class.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlaylistRepository(context: Context) {
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val uid = auth.uid

    fun newPlaylist(name: String) {
        val reference = database.getReference("$uid/playlists")

        reference.push().setValue(name)
    }

    fun showPlaylists(callback: (String?) -> Unit) {
        val reference = database.getReference("$uid/playlists")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //
            }

            override fun onDataChange(p0: DataSnapshot) {
                val list = p0.children

                list.forEach { playlistNode ->
                    val playlist = playlistNode.getValue(String::class.java)

                    callback(playlist)
                }
            }
        })
    }

    fun verifyPlaylist(musicId: Int, playlist: String, callback: (id: Int?) -> Unit) {
        val a = database.reference
        val query = a.child("$uid/$playlist").orderByChild("id").equalTo(musicId.toDouble())

        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var i = 0
                snapshot.children.forEach { result ->
                    i++
                    val id = result.child("id").getValue(Int::class.java)
                    callback(id)
                }

                if (i == 0) {
                    callback(null)
                }
            }
        })
    }

    fun addPlaylist(music: Music, playlist: String) {
        val favorites = database.getReference("$uid/$playlist/${music.id}")

        favorites.setValue(music)
    }

    fun playlist(playlist: String, callback: (musicSet: MutableSet<Music>) -> Unit) {
        val query = database.getReference("$uid/$playlist")
        val result = mutableSetOf<Music>()

        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //
            }

            override fun onDataChange(p0: DataSnapshot) {
                val list = p0.children

                list.forEach { child ->
                    val music = child.getValue(Music::class.java)

                    if (music != null) {
                        result.add(music)
                    }
                }

                callback(result)
            }
        })
    }

    fun removeFromPlaylist(playlist: String, musicId: Int) {
        val remove = database.getReference("$uid/$playlist/$musicId")
        remove.removeValue()
    }

    fun deletePlaylist(playlist: String) {
        val reference = database.getReference("$uid/$playlist")
        val playlistsNode = database.getReference("$uid/playlists")

        reference.removeValue()

        playlistsNode.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //
            }

            override fun onDataChange(p0: DataSnapshot) {
                val list = p0.children

                val key = run stop@{
                    list.forEach { datasnap ->
                        if (datasnap.getValue(String::class.java) == playlist) return@stop datasnap.key
                    }
                }

                if (key != Unit) {
                    val playlistReference = database.getReference("$uid/playlists/$key")
                    playlistReference.removeValue()
                }
            }

        })
    }

    fun saveUserMap(user: User) {
        if (uid != null) {
            val reference = database.getReference("maps/$uid")

            user.uid = uid
            reference.setValue(user)
        }
    }

    fun removeUserMap() {
        if (uid != null) {
            val reference = database.getReference("maps/$uid")

            reference.removeValue()
        }
    }
}