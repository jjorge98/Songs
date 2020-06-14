package br.iesb.songs.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.views.login.LoginActivity
import br.iesb.songs.views.login.RegisterActivity
import kotlinx.android.synthetic.main.activity_main_inicial.*

class MainActivity : AppCompatActivity() {
    private val viewModelL: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_inicial)

        id_cadastrar.setOnClickListener { cadastrar() }
        id_login.setOnClickListener { login() }
        id_chatbot.setOnClickListener { chatbot() }

    }

    private fun cadastrar() {
        val operation = Intent(this, RegisterActivity::class.java)
        startActivity(operation)
    }

    private fun login() {
        viewModelL.verifyLogin { response ->
            if (response == 0) {
                val operation = Intent(this, LoginActivity::class.java)
                startActivity(operation)
            } else {
                val intentLogin = Intent(this, PrincipalActivity::class.java)
                startActivity(intentLogin)
            }
        }
    }

    private fun chatbot() {
        val operation = Intent(this, ChatbotActivity::class.java)
        startActivity(operation)
    }
}
