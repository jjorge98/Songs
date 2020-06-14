package br.iesb.songs.views.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.views.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        id_butao_cadastrar.setOnClickListener { newAccount() }
        id_voltar_menu.setOnClickListener { backMenu() }
    }


    private fun newAccount() {
        val email = id_email_cadastro.text.toString()
        val password = id_senha_cadastrar.text.toString()
        val confirmaPassword = id_confirmar_senha.text.toString()

        viewModel.register(email, password, confirmaPassword) { result ->
            Toast.makeText(this, result[1], Toast.LENGTH_LONG).show()
            if (result[0] == "OK") {
                val intentLogin = Intent(this, MainActivity::class.java)
                startActivity(intentLogin)
            }
        }
    }

    private fun backMenu() {
        val intentBackMenu = Intent(this, MainActivity::class.java)
        startActivity(intentBackMenu)
    }

}
