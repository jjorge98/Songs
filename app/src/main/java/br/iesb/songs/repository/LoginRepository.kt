package br.iesb.songs.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class LoginRepository(context: Context) {
    //variável que pega a instancia do firebase auth
    private val auth = FirebaseAuth.getInstance()

    fun verifyLogin(callback: (result: Int) -> Unit){
        auth.addAuthStateListener {v ->
            if(v.currentUser == null){
                callback(0)
            } else{
                callback(1)
            }
        }
    }

//    firebase.auth().onAuthStateChanged(function(user) {
//        if (user) {
//            // User is signed in.
//        } else {
//            // No user is signed in.
//        }
//    });

    //função de recuperação de senha que recebe um email e um callback
    fun recuperarSenha(email: String, callback: (result: String?) -> Unit) {
        //variável que recebe a operação de enviar email de resetar senha
        val operation = auth.sendPasswordResetEmail(email)

        //Coloca o listener para quando completar, a gente verificar se teve sucesso ou falha
        operation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback("OK")
            } else {
                //variável de erro pode ser nula, caso não encontre a mensagem de erro da tarefa
                val error = task.exception?.localizedMessage
                callback(error)
            }
        }
    }

    //função de login que recebe um email, uma senha e um callback
    fun login(email: String, password: String, callback: (result: String?) -> Unit) {
        Log.w(TAG, "${auth.currentUser}")
        //variável que recebe a operação de login
        val operation = auth.signInWithEmailAndPassword(email, password)

        //Coloca o listener para quando completar, a gente verificar se teve sucesso ou falha
        operation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback("OK")
            } else {
                //variável de erro pode ser nula, caso não encontre a mensagem de erro da tarefa
                val error = task.exception?.localizedMessage
                callback(error)
            }
        }
    }

    //função de cadastro que recebe um email, uma senha e um callback
    fun register(email: String, password: String, callback: (result: String?) -> Unit) {
        //variável que recebe a operação de cadastro
        val operation = auth.createUserWithEmailAndPassword(email, password)

        //Coloca o listener para quando completar, a gente verificar se teve sucesso ou falha
        operation.addOnCompleteListener { task ->
            if(task.isSuccessful){
                callback("OK")
            } else{
                //variável de erro pode ser nula, caso não encontre a mensagem de erro da tarefa
                val error = task.exception?.localizedMessage
                callback(error)
            }
        }
    }

    fun signOut(){
        auth.signOut()
    }
}
