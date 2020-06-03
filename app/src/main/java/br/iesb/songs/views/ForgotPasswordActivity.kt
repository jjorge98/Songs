package br.iesb.songs.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.LoginViewModel
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    private val viewModel:LoginViewModel by lazy{
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        id_botao_recuperar.setOnClickListener{recuperarSenha()}
        id_voltar_menu.setOnClickListener{backMain()}
        id_voltar_login.setOnClickListener{backLogin()}
    }

    private fun recuperarSenha(){
        val email = id_email_recuperar.text.toString()

        viewModel.recuperarSenha(email){result ->
            Toast.makeText(this, result[1], Toast.LENGTH_LONG).show()
            if(result[0] == "OK"){
                val intentMain = Intent(this, LoginActivity::class.java)
                startActivity(intentMain)
            }
        }
    }

    private fun backMain(){
        val intentMain = Intent(this, MainInicialActivity::class.java)
        startActivity(intentMain)
    }

    private fun backLogin(){
        val intentMain = Intent(this, LoginActivity::class.java)
        startActivity(intentMain)
    }

}
