package br.iesb.songs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_inicial.*

class MainInicialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_inicial)

        id_cadastrar.setOnClickListener{cadastrar()}
        id_login.setOnClickListener{login()}

    }

    private fun cadastrar(){
        val operation = Intent(this, RegisterActivity::class.java)
        startActivity(operation)
    }

    private fun login(){
        val operation = Intent(this, LoginActivity::class.java)
        startActivity(operation)
    }

}
