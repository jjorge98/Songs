package br.iesb.songs.views.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.views.MainActivity
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        id_botao_recuperar.setOnClickListener { recuperarSenha() }
        id_voltar_menu.setOnClickListener { backMain() }
        id_voltar_login.setOnClickListener { backLogin() }

        backForgotPassword.setOnTouchListener { _, _ ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun recuperarSenha() {
        val email = id_email_recuperar.text.toString()

        viewModel.recuperarSenha(email) { result ->
            Toast.makeText(this, result[1], Toast.LENGTH_LONG).show()
            if (result[0] == "OK") {
                val intentMain = Intent(this, LoginActivity::class.java)
                startActivity(intentMain)
            }
        }
    }

    private fun backMain() {
        val intentMain = Intent(this, MainActivity::class.java)
        startActivity(intentMain)
    }

    private fun backLogin() {
        val intentMain = Intent(this, LoginActivity::class.java)
        startActivity(intentMain)
    }

}
