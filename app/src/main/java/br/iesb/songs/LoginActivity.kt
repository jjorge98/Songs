package br.iesb.songs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.action_bar))

        id_button_entrar.setOnClickListener{login()}
        id_recuperar_senha.setOnClickListener{forgotPassword()}
        id_volttar_menu_inicial.setOnClickListener{backMenu()}


    }
    private fun login(){
        val email = id_email.text.toString()
        val password = id_senha_cadastrar.text.toString()

        if(email.isEmpty()){
            Toast.makeText(this, "E-mail obrigatório!", Toast.LENGTH_LONG).show()
            return
        } else if (password.isEmpty()){
            Toast.makeText(this, "Senha obrigatória!", Toast.LENGTH_LONG).show()
            return
        } else {
            val operation = auth.signInWithEmailAndPassword(email, password)

            operation.addOnCompleteListener{task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_LONG).show()
                    val intentLogin = Intent(this, MainActivity::class.java)
                    startActivity(intentLogin)
                } else{
                    val error = task.exception?.localizedMessage
                        ?: "Não foi possível fazer o login. Por favor contate o adm do sistema!"
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun forgotPassword(){
        val intentForgotPassword = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intentForgotPassword)
    }
    private fun backMenu(){
        val intentBackMenu = Intent(this, MainInicialActivity::class.java)
        startActivity(intentBackMenu)
    }
}

