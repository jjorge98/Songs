package br.iesb.songs.interactor

import android.content.Context
import br.iesb.songs.repository.LoginRepository

class LoginInteractor(private val context: Context) {
    //variável que chama o repository
    private val repository = LoginRepository(context)

    fun verifyLogin(callback: (result: Int) -> Unit){
        repository.verifyLogin(callback)
    }

    //função de recuperar senha que recebe um email e um callback
    fun recuperarSenha(email: String, callback: (result: String?) -> Unit) {
        //Faz a verificação necessária (regras de negócio)
        //Se estiver tudo ok (else), ele chama a função do repository
        if (email.isEmpty()) {
            callback("VAZIO")
        } else {
            //Como o resultado vai vir da repository e não precisa fazer nenhuma verificação
            // do que vem de la, passa-se o callback como parâmetro para a função do repository
            // já fazer o feedback pra view model
            repository.recuperarSenha(email, callback)
        }
    }

    //função de login que recebe um email e uma senha e um callback
    fun login(email: String, password: String, callback: (result: String?) -> Unit) {
        //Faz a verificação necessária (regras de negócio)
        //Se estiver tudo ok (else), ele chama a função do repository
        if (email.isEmpty() || password.isEmpty()) {
            callback("VAZIO")
        } else {
            //Como o resultado vai vir da repository e não precisa fazer nenhuma verificação
            // do que vem de la, passa-se o callback como parâmetro para a função do repository
            // já fazer o feedback pra view model
            repository.login(email, password, callback)
        }
    }

    //função de cadastro que recebe um email, uma senha e uma confirmação de senha e um callback
    fun register(
        email: String,
        password: String,
        confirmPassword: String,
        callback: (result: String?) -> Unit
    ) {
        //Faz a verificação necessária (regras de negócio)
        //Se estiver tudo ok (else), ele chama a função do repository
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            callback("VAZIO")
        } else if (password != confirmPassword) {
            callback("SENHAS")
        } else if (password.length < 6) {
            callback("SENHA")
        } else {
            //Como o resultado vai vir da repository e não precisa fazer nenhuma verificação
            // do que vem de la, passa-se o callback como parâmetro para a função do repository
            // já fazer o feedback pra view model
            repository.register(email, password, callback)
        }
    }

    fun signOut(){
        repository.signOut()
    }
}
