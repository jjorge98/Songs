package br.iesb.songs.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import br.iesb.songs.R

import kotlinx.android.synthetic.main.activity_chatbot.*


class ChatbotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        id_backMenuInicialFloating.setOnClickListener { backListFavoritos() }
    }

    private fun backListFavoritos() {
        val intentMain = Intent(this, MainInicialActivity::class.java)
        startActivity(intentMain)
    }

}
