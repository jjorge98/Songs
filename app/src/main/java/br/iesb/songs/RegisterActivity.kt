package br.iesb.songs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        id_butao_cadastrar.setOnClickListener{newAccout()}
        id_voltar_menu.setOnClickListener{backMenu()}
    }


    private fun newAccout(){
        val email = id_email_cadastro.text.toString()
        val password = id_senha_cadastrar.text.toString()
        val confirmaPassword = id_confirmar_senha.text.toString()

        if(email.isEmpty()){
            Toast.makeText(this, "E-mail obrigatório!", Toast.LENGTH_LONG).show()
            return
        } else if (password.isEmpty()){
            Toast.makeText(this, "Senha obrigatória!", Toast.LENGTH_LONG).show()
            return
        } else if (confirmaPassword.isEmpty()){
            Toast.makeText(this, "Confirma Senha obrigatória!", Toast.LENGTH_LONG).show()
            return
        } else if (password != confirmaPassword){
            Toast.makeText(this, "Essas senhas não coincidem. Tente novamente!", Toast.LENGTH_LONG).show()
            return
        }else {
            val operation = auth.createUserWithEmailAndPassword(email, password)

            operation.addOnCompleteListener{task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Cadastro efetuado com sucesso!", Toast.LENGTH_LONG).show()
                    val intentLogin = Intent(this, MainInicialActivity::class.java)
                    startActivity(intentLogin)
                } else{
                    val error = task.exception?.localizedMessage
                        ?: "Não foi possível fazer o cadastro. Por favor contate o adm do sistema!"
                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun backMenu(){
        val intentBackMenu = Intent(this, MainInicialActivity::class.java)
        startActivity(intentBackMenu)
    }

}
