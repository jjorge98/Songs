package br.iesb.songs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        id_botao_recuperar.setOnClickListener{recuperarSenha()}
        id_voltar_menu.setOnClickListener{backMain()}
        id_voltar_login.setOnClickListener{backLogin()}

    }

    private fun recuperarSenha(){
        val email = id_email_recuperar.text.toString()

        if(email.isEmpty()){
            Toast.makeText(this, "E-mail obrigatório!", Toast.LENGTH_LONG).show()
            return
        } else{
            val operation = auth.sendPasswordResetEmail(email)
            operation.addOnCompleteListener{task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "E-mail de recuperação de senha enviado com sucesso!", Toast.LENGTH_LONG).show()
                    val intentMain = Intent(this, LoginActivity::class.java)
                    startActivity(intentMain)
                } else {
                    val error = task.exception?.localizedMessage
                        ?: "Algo deu errado ao enviar o e-mail de recuperação de senha. " +
                        "Contate o adm do sistema!"

                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                }
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
