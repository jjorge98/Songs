package br.iesb.songs.views.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.views.MainActivity
import br.iesb.songs.views.fragments.UserNameFragment
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

        backRegister.setOnTouchListener { _, _ ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }


    private fun newAccount() {
        val email = id_email_cadastro.text.toString()
        val password = id_senha_cadastrar.text.toString()
        val confirmaPassword = id_confirmar_senha.text.toString()

        viewModel.register(email, password, confirmaPassword) { result ->
            Toast.makeText(this, result[1], Toast.LENGTH_LONG).show()
            if (result[0] == "OK") {
                id_butao_cadastrar.visibility = View.GONE
                val manager = supportFragmentManager

                manager.beginTransaction()
                    .add(
                        R.id.backRegister,
                        UserNameFragment("doesntExists"),
                        "userName"
                    )
                    .commit()
            }
        }
    }

    private fun backMenu() {
        val intentBackMenu = Intent(this, MainActivity::class.java)
        startActivity(intentBackMenu)
    }

}
